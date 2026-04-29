import React, { useEffect, useState } from 'react';
import { Alert, ActivityIndicator, View } from 'react-native';
import { useRoute, RouteProp, useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import { useAuth } from '../../context/AuthContext';
import ParkingDetailView from './ParkingDetailView';
import parkingService, { Parking, ParkingSpot } from '../../services/parkingService';

type ParkingDetailRouteProp = RouteProp<RootStackParamList, 'ParkingDetail'>;

const ParkingDetailScreen = () => {
  const route = useRoute<ParkingDetailRouteProp>();
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();
  const { parkingId } = route.params;
  const { userToken } = useAuth();

  const [parking, setParking] = useState<Parking | null>(null);
  const [spots, setSpots] = useState<ParkingSpot[]>([]);
  const [loading, setLoading] = useState(true);
  const [selectedSpotId, setSelectedSpotId] = useState<string | null>(null);

  useEffect(() => {
    loadData();
  }, [parkingId]);

  const loadData = async () => {
    try {
      setLoading(true);
      const [parkingData, spotsData] = await Promise.all([
        parkingService.getById(parseInt(parkingId)),
        parkingService.getAvailableSpots(parseInt(parkingId))
      ]);
      setParking(parkingData);
      setSpots(spotsData);
    } catch (error) {
      console.error('Error loading parking detail:', error);
      Alert.alert('Error', 'No se pudo cargar la información del parking.');
    } finally {
      setLoading(false);
    }
  };

  const handleReserve = () => {
    if (!selectedSpotId) return;

    if (!userToken) {
      Alert.alert(
        'Login Required',
        'You need to be logged in to make a reservation.',
        [
          { text: 'Cancel', style: 'cancel' },
          { text: 'Login', onPress: () => navigation.navigate('Login') },
        ]
      );
    } else {
      const selectedSpot = spots.find(s => s.id.toString() === selectedSpotId);
      navigation.navigate('ReservationConfirm', { 
        spotId: selectedSpotId,
        parkingId: parkingId,
        spotNumber: selectedSpot?.number || 0
      });
    }
  };

  if (loading) {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <ActivityIndicator size="large" color="#3B82F6" />
      </View>
    );
  }

  return (
    <ParkingDetailView 
      parkingName={parking?.name || 'Parking Detail'}
      address={parking?.address}
      spots={spots}
      selectedSpotId={selectedSpotId}
      onSelectSpot={setSelectedSpotId}
      onReserve={handleReserve}
    />
  );
};

export default ParkingDetailScreen;

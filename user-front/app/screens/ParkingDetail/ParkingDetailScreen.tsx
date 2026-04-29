import React, { useEffect, useState } from 'react';
import { Alert, ActivityIndicator, View } from 'react-native';
import { useRoute, RouteProp, useNavigation, useIsFocused } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import { useAuth } from '../../context/AuthContext';
import ParkingDetailView from './ParkingDetailView';
import parkingService, { Parking, ParkingSpot } from '../../services/parkingService';
import reservationService from '../../services/reservationService';

type ParkingDetailRouteProp = RouteProp<RootStackParamList, 'ParkingDetail'>;

const ParkingDetailScreen = () => {
  const route = useRoute<ParkingDetailRouteProp>();
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();
  const isFocused = useIsFocused();
  const { parkingId } = route.params;
  const { userToken } = useAuth();

  const [parking, setParking] = useState<Parking | null>(null);
  const [spots, setSpots] = useState<ParkingSpot[]>([]);
  const [loading, setLoading] = useState(true);
  const [selectedSpotId, setSelectedSpotId] = useState<string | null>(null);
  const [occupiedReservations, setOccupiedReservations] = useState<any[]>([]);
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [startHour, setStartHour] = useState(new Date().getHours() + 1);
  const [endHour, setEndHour] = useState(new Date().getHours() + 2);

  useEffect(() => {
    if (isFocused) {
      loadData();
    }
  }, [isFocused, parkingId]);

  const loadData = async () => {
    try {
      setLoading(true);
      const id = parseInt(parkingId);
      
      const [parkingData, spotsData] = await Promise.all([
        parkingService.getById(id),
        parkingService.getAllSpots(id)
      ]);
      setParking(parkingData);
      setSpots(spotsData);

      try {
        const reservationsData = await reservationService.getOccupiedByParking(id);
        setOccupiedReservations(reservationsData);
      } catch (resError) {
        console.warn('Could not fetch reservations for filtering:', resError);
        setOccupiedReservations([]);
      }
      
    } catch (error) {
      console.error('Error loading parking essential detail:', error);
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
        spotNumber: selectedSpot?.number || 0,
        initialDate: selectedDate.toISOString(),
        initialStartHour: startHour,
        initialEndHour: endHour
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
      selectedDate={selectedDate}
      onDateChange={setSelectedDate}
      startHour={startHour}
      onStartHourChange={setStartHour}
      endHour={endHour}
      onEndHourChange={setEndHour}
      occupiedReservations={occupiedReservations}
    />
  );
};

export default ParkingDetailScreen;

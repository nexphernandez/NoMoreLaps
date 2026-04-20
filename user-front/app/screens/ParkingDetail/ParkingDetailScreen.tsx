import React, { useState } from 'react';
import { Alert } from 'react-native';
import { useRoute, RouteProp, useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import { useAuth } from '../../context/AuthContext'; // Importamos el contexto
import ParkingDetailView from './ParkingDetailView';

type ParkingDetailRouteProp = RouteProp<RootStackParamList, 'ParkingDetail'>;

const ParkingDetailScreen = () => {
  const route = useRoute<ParkingDetailRouteProp>();
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();
  const { parkingId } = route.params;
  const { userToken } = useAuth();

  const [selectedSpotId, setSelectedSpotId] = useState<string | null>(null);

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
      // @ts-ignore
      navigation.navigate('ReservationConfirm');
    }
  };

  return (
    <ParkingDetailView 
      parkingName={`Parking Area ${parkingId}`}
      selectedSpotId={selectedSpotId}
      onSelectSpot={setSelectedSpotId}
      onReserve={handleReserve}
    />
  );
};

export default ParkingDetailScreen;

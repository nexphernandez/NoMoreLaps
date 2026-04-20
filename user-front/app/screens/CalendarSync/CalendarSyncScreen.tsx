import React, { useState } from 'react';
import CalendarSyncView, { CalendarEvent } from './CalendarSyncView';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';

const MOCK_EVENTS: CalendarEvent[] = [
  {
    id: '1',
    title: 'Dentist Appointment',
    time: '10:00 AM',
    location: 'Calle Real, 15, Madrid',
    suggestedParking: {
      name: 'Centro Real Parking',
      distance: '150m away',
      price: '2.50€/h'
    }
  },
  {
    id: '2',
    title: 'Business Meeting',
    time: '02:30 PM',
    location: 'Paseo de la Castellana, 100',
    suggestedParking: {
      name: 'Castellana Tower Parking',
      distance: '300m away',
      price: '3.20€/h'
    }
  },
  {
    id: '3',
    title: 'Gym Session',
    time: '06:00 PM',
    location: 'Avenida de América, 45',
    // No suggeessted parking here to test variety
  }
];

const CalendarSyncScreen = () => {
  const [isSynced, setIsSynced] = useState(false);
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();

  const handleSync = () => {
    // Simulating a sync process
    setIsSynced(true);
  };

  const handleReserve = (parkingId: string) => {
    // Navigate to parking detail
    navigation.navigate('ParkingDetail', { parkingId: '1' });
  };

  return (
    <CalendarSyncView
      isSynced={isSynced}
      onSync={handleSync}
      events={MOCK_EVENTS}
      onReserve={handleReserve}
    />
  );
};

export default CalendarSyncScreen;

import React, { useState, useEffect } from 'react';
import ReservationConfirmView, { ReservationSummary } from './ReservationConfirmView';
import { useNavigation, useRoute, RouteProp } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import { useAuth } from '../../context/AuthContext';
import { Alert, ActivityIndicator, View } from 'react-native';
import Typography from '../../components/Typography';
import reservationService from '../../services/reservationService';
import parkingService from '../../services/parkingService';
import calendarService from '../../services/calendarService';

type ReservationConfirmRouteProp = RouteProp<RootStackParamList, 'ReservationConfirm'>;

const ReservationConfirmScreen = () => {
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();
  const route = useRoute<ReservationConfirmRouteProp>();
  
  const { spotId, parkingId, spotNumber, initialDate, initialStartHour, initialEndHour } = route.params;
  const { user } = useAuth();
  
  const [loading, setLoading] = useState(true);
  const [parking, setParking] = useState<any>(null);
  const [processing, setProcessing] = useState(false);
  
  const [selectedDate, setSelectedDate] = useState(initialDate ? new Date(initialDate) : new Date());
  const [startHour, setStartHour] = useState(initialStartHour !== undefined ? initialStartHour : new Date().getHours() + 1);
  const [endHour, setEndHour] = useState(initialEndHour !== undefined ? initialEndHour : new Date().getHours() + 2);

  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);
        const data = await parkingService.getById(parseInt(parkingId));
        setParking(data);
      } catch (error) {
        console.error("Error loading parking info:", error);
      } finally {
        setLoading(false);
      }
    };
    loadData();
  }, [parkingId]);

  const calculatePrice = () => {
    if (!parking) return "0.00";
    const durationHours = endHour - startHour;
    const pricePerHour = parking.pricePerHour || 2.0;
    return (durationHours * pricePerHour).toFixed(2); 
  };

  const formatLocalDate = (date: Date, hour: number) => {
    const d = new Date(date);
    const h = Math.floor(hour);
    const m = (hour % 1) === 0.5 ? 30 : 0;
    d.setHours(h, m, 0, 0);

    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');
    const hh = String(d.getHours()).padStart(2, '0');
    const mm = String(d.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day}T${hh}:${mm}:00`;
  };

  const handleConfirm = async () => {
    if (!user) {
      Alert.alert("Error", "User not detected.");
      return;
    }
    
    setProcessing(true);
    try {
      await reservationService.create({
        parkingSpotId: parseInt(spotId),
        userId: user.id,
        startTime: formatLocalDate(selectedDate, startHour),
        endTime: formatLocalDate(selectedDate, endHour),
        price: parseFloat(calculatePrice()),
        state: 'ACTIVE'
      });

      Alert.alert(
        'Reservation Successful',
        `Spot P-${spotNumber} has been reserved successfully.`,
        [{ text: 'Ok', onPress: () => navigation.replace('ReservationHistory') }]
      );
    } catch (error: any) {
      Alert.alert('Error', error.message || 'Reservation failed.');
    } finally {
      setProcessing(false);
    }
  };

  const formatTime = (t: number) => {
    const h = Math.floor(t);
    const m = (t % 1) === 0.5 ? '30' : '00';
    return `${h < 10 ? `0${h}` : h}:${m}`;
  };

  const calculateDuration = () => {
    const totalHours = endHour - startHour;
    const h = Math.floor(totalHours);
    const m = (totalHours % 1) * 60;
    return `${h}h ${m > 0 ? `${m}m` : ''}`;
  };

  if (loading || processing) {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <ActivityIndicator size="large" color="#3B82F6" />
      </View>
    );
  }

  const finalSummaryData: ReservationSummary = {
    parkingName: parking?.name || '...',
    spot: spotNumber.toString(),
    price: `${calculatePrice()}€`,
    startTime: formatTime(startHour),
    endTime: formatTime(endHour),
    date: selectedDate.toLocaleDateString(),
    duration: calculateDuration(),
    sanctionPolicy: parking ? `${parking.sanctionAmount}€ / ${parking.sanctionIntervalInMinutes}min` : '5€/30min',
    hasSanction: (parking?.sanctionAmount || 0) > 0
  };

  return (
    <ReservationConfirmView
      summary={finalSummaryData}
      onConfirm={handleConfirm}
      onCancel={() => navigation.goBack()}
    />
  );
};

export default ReservationConfirmScreen;

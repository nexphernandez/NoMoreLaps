import React, { useState, useEffect } from 'react';
import ReservationConfirmView, { ReservationSummary } from './ReservationConfirmView';
import { useNavigation, useRoute, RouteProp } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import { usePayment } from '../../context/PaymentContext';
import { useAuth } from '../../context/AuthContext';
import { Alert, ActivityIndicator, View } from 'react-native';
import { PaymentMethod } from '../../context/PaymentContext';
import Typography from '../../components/Typography';
import reservationService from '../../services/reservationService';
import parkingService from '../../services/parkingService';
import calendarService from '../../services/calendarService';

type ReservationConfirmRouteProp = RouteProp<RootStackParamList, 'ReservationConfirm'>;

const ReservationConfirmScreen = () => {
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();
  const route = useRoute<ReservationConfirmRouteProp>();
  const { spotId, parkingId, spotNumber } = route.params;
  const { methods, defaultMethod, hasPaymentMethod } = usePayment();
  const { user } = useAuth();
  
  const [chosenCard, setChosenCard] = useState<PaymentMethod | null>(defaultMethod || null);
  const [loading, setLoading] = useState(true);
  const [parkingName, setParkingName] = useState('');
  const [processing, setProcessing] = useState(false);

  useEffect(() => {
    loadParkingInfo();
    if (!chosenCard && defaultMethod) {
      setChosenCard(defaultMethod);
    }
  }, [defaultMethod, parkingId]);

  const loadParkingInfo = async () => {
    try {
      const data = await parkingService.getById(parseInt(parkingId));
      setParkingName(data.name);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  const summary: ReservationSummary = {
    parkingName: parkingName || 'Parking Loading...',
    spot: `P-${spotNumber}`,
    price: '2.00€/h',
    startTime: 'Now',
    duration: 'Estimated 1h'
  };

  const handleChangeCard = () => {
    if (methods.length <= 1) {
      Alert.alert('Info', 'No tienes otras tarjetas guardadas.');
      return;
    }

    Alert.alert(
      'Seleccionar Tarjeta',
      'Elige la tarjeta para este pago:',
      methods.map(card => ({
        text: `${card.brand} **** ${card.last4}`,
        onPress: () => setChosenCard(card)
      })).concat([{ text: 'Cancelar', style: 'cancel' }] as any)
    );
  };

  const handleConfirm = () => {
    if (!hasPaymentMethod || !chosenCard) {
      Alert.alert(
        'No hay tarjeta vinculada',
        'Necesitas añadir un método de pago para realizar la reserva.',
        [
          { text: 'Ahora no', style: 'cancel' },
          { text: 'Añadir tarjeta', onPress: () => navigation.navigate('PaymentMethods') }
        ]
      );
      return;
    }

    Alert.alert(
      '¿Confirmar Reserva?',
      `Vas a reservar la plaza ${summary.spot} en "${summary.parkingName}". ¿Estás seguro?`,
      [
        { text: 'No, revisar', style: 'cancel' },
        { text: 'Sí, Reservar ahora', onPress: () => processReservation() }
      ]
    );
  };

  const processReservation = async () => {
    if (!user) return;
    
    setProcessing(true);
    try {
      const now = new Date();
      const end = new Date(now.getTime() + 60 * 60 * 1000); // 1 hour from now

      await reservationService.create({
        parkingSpotId: parseInt(spotId),
        userId: user.id,
        startTime: now.toISOString(),
        endTime: end.toISOString(),
        price: 2.00,
        state: 'ACTIVA'
      });

      // SYNC TO CALENDAR
      await calendarService.addToCalendar({
        title: `Parking Reservation: ${summary.parkingName}`,
        startDate: now.toISOString(),
        endDate: end.toISOString(),
        location: summary.parkingName,
        notes: `Reserved Spot: ${summary.spot}`
      });

      Alert.alert(
        'Reserva Realizada',
        `Reserva creada con éxito. El pago de 2.00€ se ha procesado con tu ${chosenCard?.brand}.`,
        [{ text: 'Ver mis Reservas', onPress: () => navigation.replace('ReservationHistory') }]
      );
    } catch (error: any) {
      Alert.alert('Error', error.message || 'No se pudo procesar la reserva.');
    } finally {
      setProcessing(false);
    }
  };

  if (loading || processing) {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <ActivityIndicator size="large" color="#3B82F6" />
        {processing && <Typography variant="h3" style={{marginTop: 10}}>Procesando Reserva...</Typography>}
      </View>
    );
  }

  return (
    <ReservationConfirmView
      summary={summary}
      selectedCard={chosenCard}
      onConfirm={handleConfirm}
      onCancel={() => navigation.goBack()}
      onChangeCard={handleChangeCard}
    />
  );
};

export default ReservationConfirmScreen;

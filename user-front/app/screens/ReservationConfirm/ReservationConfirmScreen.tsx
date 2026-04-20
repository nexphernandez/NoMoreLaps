import React from 'react';
import ReservationConfirmView, { ReservationSummary } from './ReservationConfirmView';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import { usePayment } from '../../context/PaymentContext';
import { Alert } from 'react-native';
import { PaymentMethod } from '../PaymentMethods/PaymentMethodsView';
const ReservationConfirmScreen = () => {
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();
  const { methods, defaultMethod, hasPaymentMethod } = usePayment();
  const [chosenCard, setChosenCard] = React.useState<PaymentMethod | null>(defaultMethod || null);

  // Sync chosen card if default changes or we just loaded
  React.useEffect(() => {
    if (!chosenCard && defaultMethod) {
      setChosenCard(defaultMethod);
    }
  }, [defaultMethod]);

  const mockSummary: ReservationSummary = {
    parkingName: 'Plaza Mayor Parking',
    spot: 'B-04',
    price: '2.50€/h',
    startTime: 'Hoy, 14:30',
    duration: '2 Horas'
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

    // FINAL CONFIRMATION
    Alert.alert(
      '¿Confirmar Reserva?',
      `Vas a reservar el parking "${mockSummary.parkingName}" por ${mockSummary.price}. ¿Estás seguro?`,
      [
        { text: 'No, revisar', style: 'cancel' },
        { text: 'Sí, Reservar ahora', onPress: () => processReservation() }
      ]
    );
  };

  const processReservation = () => {
    Alert.alert(
      'Reserva Realizada',
      `Pago de ${mockSummary.price} procesado con éxito con tu ${chosenCard?.brand}.\n\nTienes 15 minutos para llegar.`,
      [{ text: 'Entendido', onPress: () => navigation.navigate('Home') }]
    );
  };

  const handleCancel = () => {
    navigation.goBack();
  };

  return (
    <ReservationConfirmView
      summary={mockSummary}
      selectedCard={chosenCard}
      onConfirm={handleConfirm}
      onCancel={handleCancel}
      onChangeCard={handleChangeCard}
    />
  );
};

export default ReservationConfirmScreen;

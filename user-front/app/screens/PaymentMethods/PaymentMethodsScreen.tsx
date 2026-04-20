import React from 'react';
import PaymentMethodsView from './PaymentMethodsView';
import { usePayment, PaymentMethod } from '../../context/PaymentContext';
import { Alert } from 'react-native';

const PaymentMethodsScreen = () => {
  const { methods, addMethod, deleteMethod, setDefaultMethod } = usePayment();

  const handleAddMethod = () => {
    // Simulamos añadir una tarjeta nueva para la demo
    const newCard: PaymentMethod = {
      id: Math.random().toString(),
      brand: 'Mastercard',
      last4: '1234',
      expiry: '08/27',
      isDefault: methods.length === 0
    };
    addMethod(newCard);
    Alert.alert('Card Added', 'A new test card has been added to your wallet.');
  };

  return (
    <PaymentMethodsView
      methods={methods}
      onAddMethod={handleAddMethod}
      onDeleteMethod={deleteMethod}
      onSetDefault={setDefaultMethod}
    />
  );
};

export default PaymentMethodsScreen;

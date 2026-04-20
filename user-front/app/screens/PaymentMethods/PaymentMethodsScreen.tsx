import React, { useState } from 'react';
import PaymentMethodsView, { PaymentMethod } from './PaymentMethodsView';
import { Alert } from 'react-native';

const MOCK_METHODS: PaymentMethod[] = [
  { id: '1', brand: 'Visa', last4: '4242', expiry: '12/26', isDefault: true },
  { id: '2', brand: 'Mastercard', last4: '8888', expiry: '05/25', isDefault: false },
];

const PaymentMethodsScreen = () => {
  const [methods, setMethods] = useState<PaymentMethod[]>(MOCK_METHODS);

  const handleAddMethod = () => {
    Alert.alert('Coming Soon', 'This feature will allow you to scan your card.');
  };

  const handleDeleteMethod = (id: string) => {
    setMethods(prev => prev.filter(m => m.id !== id));
  };

  const handleSetDefault = (id: string) => {
    setMethods(prev => prev.map(m => ({
      ...m,
      isDefault: m.id === id
    })));
  };

  return (
    <PaymentMethodsView
      methods={methods}
      onAddMethod={handleAddMethod}
      onDeleteMethod={handleDeleteMethod}
      onSetDefault={handleSetDefault}
    />
  );
};

export default PaymentMethodsScreen;

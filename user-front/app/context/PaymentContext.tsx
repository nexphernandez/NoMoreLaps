import React, { createContext, useState, useContext, useMemo } from 'react';

export interface PaymentMethod {
  id: string;
  brand: 'Visa' | 'Mastercard' | 'Paypal';
  last4: string;
  expiry: string;
  isDefault: boolean;
}

interface PaymentContextType {
  methods: PaymentMethod[];
  addMethod: (method: PaymentMethod) => void;
  deleteMethod: (id: string) => void;
  setDefaultMethod: (id: string) => void;
  defaultMethod: PaymentMethod | undefined;
  hasPaymentMethod: boolean;
}

const PaymentContext = createContext<PaymentContextType | undefined>(undefined);

export const PaymentProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [methods, setMethods] = useState<PaymentMethod[]>([
    { id: '1', brand: 'Visa', last4: '4242', expiry: '12/26', isDefault: true },
  ]);

  const addMethod = (method: PaymentMethod) => {
    setMethods(prev => [...prev, method]);
  };

  const deleteMethod = (id: string) => {
    setMethods(prev => prev.filter(m => m.id !== id));
  };

  const setDefaultMethod = (id: string) => {
    setMethods(prev => prev.map(m => ({
      ...m,
      isDefault: m.id === id
    })));
  };

  const defaultMethod = useMemo(() => methods.find(m => m.isDefault), [methods]);
  const hasPaymentMethod = methods.length > 0;

  return (
    <PaymentContext.Provider value={{ 
      methods, 
      addMethod, 
      deleteMethod, 
      setDefaultMethod, 
      defaultMethod,
      hasPaymentMethod 
    }}>
      {children}
    </PaymentContext.Provider>
  );
};

export const usePayment = () => {
  const context = useContext(PaymentContext);
  if (context === undefined) {
    throw new Error('usePayment must be used within a PaymentProvider');
  }
  return context;
};

import React, { createContext, useState, useContext, useMemo, useEffect } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';

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
  isLoading: boolean;
}

const PaymentContext = createContext<PaymentContextType | undefined>(undefined);
const STORAGE_KEY = '@nomorelaps_payment_methods';

export const PaymentProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [methods, setMethods] = useState<PaymentMethod[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  // Load methods on startup
  useEffect(() => {
    const loadMethods = async () => {
      try {
        const savedMethods = await AsyncStorage.getItem(STORAGE_KEY);
        if (savedMethods) {
          setMethods(JSON.parse(savedMethods));
        }
      } catch (error) {
        console.error('Error loading payment methods:', error);
      } finally {
        setIsLoading(false);
      }
    };
    loadMethods();
  }, []);

  // Save methods whenever they change
  useEffect(() => {
    const saveMethods = async () => {
      if (!isLoading) {
        try {
          await AsyncStorage.setItem(STORAGE_KEY, JSON.stringify(methods));
        } catch (error) {
          console.error('Error saving payment methods:', error);
        }
      }
    };
    saveMethods();
  }, [methods, isLoading]);

  const addMethod = (method: PaymentMethod) => {
    setMethods(prev => {
      const updated = [...prev, method];
      // If it's the first method, make it default
      if (updated.length === 1) updated[0].isDefault = true;
      return updated;
    });
  };

  const deleteMethod = (id: string) => {
    setMethods(prev => {
      const filtered = prev.filter(m => m.id !== id);
      // If we deleted the default, set another one as default
      if (filtered.length > 0 && !filtered.some(m => m.isDefault)) {
        filtered[0].isDefault = true;
      }
      return filtered;
    });
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
      hasPaymentMethod,
      isLoading
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

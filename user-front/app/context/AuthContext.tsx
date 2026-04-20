import React, { createContext, useState, useContext, useEffect } from 'react';
import * as SecureStore from 'expo-secure-store';

export interface UserData {
  name: string;
  email: string;
  phone: string;
  avatar?: string;
}

interface AuthContextType {
  userToken: string | null;
  user: UserData | null;
  isLoading: boolean;
  login: (token: string, userData: UserData) => Promise<void>;
  logout: () => Promise<void>;
  updateUser: (data: Partial<UserData>) => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [userToken, setUserToken] = useState<string | null>(null);
  const [user, setUser] = useState<UserData | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const updateUser = (newData: Partial<UserData>) => {
    setUser(prev => prev ? { ...prev, ...newData } : null);
  };

  useEffect(() => {
    const loadStorageData = async () => {
      try {
        const token = await SecureStore.getItemAsync('userToken');
        const userData = await SecureStore.getItemAsync('userData');
        
        if (token) setUserToken(token);
        if (userData) setUser(JSON.parse(userData));
      } catch (e) {
        console.error('Error recovering storage data', e);
      } finally {
        setIsLoading(false);
      }
    };
    loadStorageData();
  }, []);

  const login = async (token: string, userData: UserData) => {
    try {
      await SecureStore.setItemAsync('userToken', token);
      await SecureStore.setItemAsync('userData', JSON.stringify(userData));
      
      setUserToken(token);
      setUser(userData);
    } catch (e) {
      console.error('Login storage error:', e);
    }
  };

  const logout = async () => {
    try {
      await SecureStore.deleteItemAsync('userToken');
      await SecureStore.deleteItemAsync('userData');
      setUserToken(null);
      setUser(null);
    } catch (e) {
      console.error('Logout storage error', e);
    }
  };

  return (
    <AuthContext.Provider value={{ userToken, user, isLoading, login, logout, updateUser }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

import React, { createContext, useState, useContext, useEffect } from 'react';
import * as SecureStore from 'expo-secure-store';
import authService, { LoginData } from '../services/authService';


export interface UserData {
  name: string;
  email: string;
  phone: string;
  avatar?: string;
}

/**
 * Interface to describe the 'cloud' of data we are sharing.
 */
interface AuthContextType {
  userToken: string | null;
  user: UserData | null;
  isLoading: boolean;
  login: (token: string) => Promise<void>;
  logout: () => Promise<void>;
  updateUser: (data: Partial<UserData>) => void;
}

// 1. Create the Context (the container for the cloud)
const AuthContext = createContext<AuthContextType | undefined>(undefined);

// 2. The Provider component that will wrap the whole app
export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [userToken, setUserToken] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [user, setUser] = useState<UserData | null>({
    name: 'John Doe',
    email: 'user@test.com',
    phone: '+34 600 000 000'
  });
  const updateUser = (newData: Partial<UserData>) => {
    setUser(prev => prev ? { ...prev, ...newData } : null);
  };
  useEffect(() => {
    /**
     * When the app starts, check if we already have a token
     * saved in the phone from a previous session.
     */
    const loadToken = async () => {
      try {
        const token = await SecureStore.getItemAsync('userToken');
        setUserToken(token);
      } catch (e) {
        console.error('Error recovering token', e);
      } finally {
        setIsLoading(false);
      }
    };
    loadToken();
  }, []);

  const login = async (token: string) => {
    try {
      await SecureStore.setItemAsync('userToken', token);
      setUserToken(token);
    } catch (e) {
      console.error('Error al iniciar sesión:', e);
    }
  };

  const logout = async () => {
    try {
      await SecureStore.deleteItemAsync('userToken');
      setUserToken(null);
    } catch (e) {
      console.error('Logout error', e);
    }
  };

  return (
    <AuthContext.Provider value={{ userToken,user, isLoading, login, logout, updateUser }}>
      {children}
    </AuthContext.Provider>
  );
};

// 3. A custom hook to easily use this context in any component
export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

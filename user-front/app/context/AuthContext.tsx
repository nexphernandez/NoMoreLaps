import React, { createContext, useState, useContext, useEffect } from 'react';
import * as SecureStore from 'expo-secure-store';
import authService, { LoginData } from '../services/authService';

/**
 * Interface to describe the 'cloud' of data we are sharing.
 */
interface AuthContextType {
  userToken: string | null;
  isLoading: boolean;
  login: (data: LoginData) => Promise<void>;
  logout: () => Promise<void>;
}

// 1. Create the Context (the container for the cloud)
const AuthContext = createContext<AuthContextType | undefined>(undefined);

// 2. The Provider component that will wrap the whole app
export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [userToken, setUserToken] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(true);

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

  const login = async (data: LoginData) => {
    try {
      const response = await authService.login(data);
      // Save the token locally on the phone (persistence)
      await SecureStore.setItemAsync('userToken', response.token);
      // Update our state so the whole app knows we are logged in
      setUserToken(response.token);
    } catch (e) {
      throw e;
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
    <AuthContext.Provider value={{ userToken, isLoading, login, logout }}>
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

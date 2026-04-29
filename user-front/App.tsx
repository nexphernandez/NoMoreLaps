import React, { useEffect } from 'react';
import 'react-native-gesture-handler';
import { StatusBar } from 'expo-status-bar';
import { NavigationContainer } from '@react-navigation/native';
import { AuthProvider } from './app/context/AuthContext';
import { ThemeProvider } from './app/context/ThemeContext';
import RootNavigator from './app/navigation/RootNavigator';
import databaseService from './app/services/databaseService';
import syncService from './app/services/syncService';

export default function App() {
  useEffect(() => {
    const initOffline = async () => {
      try {
        await databaseService.init();
        syncService.init();
      } catch (err) {
        console.error('Failed to init offline services:', err);
      }
    };
    initOffline();
  }, []);

  return (
    <ThemeProvider>
      <AuthProvider>
        <NavigationContainer>
          <RootNavigator />
          <StatusBar style="auto" />
        </NavigationContainer>
      </AuthProvider>
    </ThemeProvider>
  );
}

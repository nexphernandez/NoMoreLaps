import React from 'react';
import HomeView from './HomeView';
import { useAuth } from '../../context/AuthContext';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';

const HomeScreen = () => {
  const { userToken } = useAuth();
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();

  const handleSelectParking = (id: string) => {
    if (!userToken) {
      // El invitado no está logueado, le mandamos al Login
      navigation.navigate('Login');
    } else {
      // El usuario está dentro, le llevamos a reservar
      console.log('Navigate to Reservation for ID:', id);
    }
  };

  return <HomeView onSelectParking={handleSelectParking} onGoToProfile={() => navigation.navigate('Login')} />;
};

export default HomeScreen;

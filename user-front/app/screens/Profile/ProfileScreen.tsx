import React from 'react';
import { useAuth } from '../../context/AuthContext';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import ProfileView from './ProfileView';

const ProfileScreen = () => {
  const { logout } = useAuth(); // Función de logout de nuestro contexto
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();

  const handleLogout = async () => {
    await logout();
    // Al cerrar sesión, lo enviamos de vuelta a la Home
    navigation.reset({
      index: 0,
      routes: [{ name: 'Home' }],
    });
  };

  return (
    <ProfileView 
      userName="John Doe" // Aquí usaremos datos del contexto luego
      userEmail="john.doe@example.com"
      activeReservation={{
        parkingName: 'Plaza Mayor Parking',
        spot: 'A-12',
        timeRemaining: '01:45:00'
      }}
      onLogout={handleLogout}
    />
  );
};

export default ProfileScreen;

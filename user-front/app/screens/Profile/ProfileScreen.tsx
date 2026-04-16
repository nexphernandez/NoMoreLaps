import React from 'react';
import { useAuth } from '../../context/AuthContext';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import ProfileView from './ProfileView';

const ProfileScreen = () => {
  const { logout } = useAuth();
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();

  const handleLogout = async () => {
    await logout();
    navigation.reset({
      index: 0,
      routes: [{ name: 'Home' }],
    });
  };
  const handleViewHistory = () => {
    navigation.navigate('ReservationHistory');
  }
    return (
      <ProfileView
        userName="John Doe" 
        userEmail="john.doe@example.com"
        activeReservation={{
          parkingName: 'Plaza Mayor Parking',
          spot: 'A-12',
          timeRemaining: '01:45:00'
        }}
        onLogout={handleLogout}
        onViewHistory={handleViewHistory}
      />
    );
  };

  export default ProfileScreen;

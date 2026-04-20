import React from 'react';
import { useAuth } from '../../context/AuthContext';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import ProfileView from './ProfileView';

const ProfileScreen = () => {
  const {user, logout } = useAuth();
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

  const handleGoToSanctions = () => {
    navigation.navigate('Sanctions');
  }

  const handleEditProfile = () => {
    navigation.navigate('EditProfile');
  };

  const handleCalendarSync = () => {
    // @ts-ignore - We will add this to the navigator next
    navigation.navigate('CalendarSync');
  };

  return (
    <ProfileView
      userName={user?.name || ''}
      userEmail={user?.email || ''}
      userAvatar={user?.avatar || null}
      activeReservation={{
        parkingName: 'Plaza Mayor Parking',
        spot: 'A-12',
        timeRemaining: '01:45:00'
      }}
      onLogout={handleLogout}
      onViewHistory={handleViewHistory}
      onGoToSanctions={handleGoToSanctions}
      onCalendarSync={handleCalendarSync}
      onEditProfile={handleEditProfile}
    />
  );
};

  export default ProfileScreen;

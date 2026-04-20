import React from 'react';
import { useAuth } from '../../context/AuthContext';
import { useTheme } from '../../context/ThemeContext';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import ProfileView from './ProfileView';

const ProfileScreen = () => {
  const { user, logout } = useAuth();
  const { themeMode, setThemeMode } = useTheme();
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
  };

  const handleGoToSanctions = () => {
    navigation.navigate('Sanctions');
  };

  const handleEditProfile = () => {
    navigation.navigate('EditProfile');
  };

  const handleCalendarSync = () => {
    navigation.navigate('CalendarSync');
  };

  const handlePaymentMethods = () => {
    navigation.navigate('PaymentMethods');
  };

  return (
    <ProfileView
      userName={user?.name || 'Guest User'}
      userEmail={user?.email || 'guest@nomorelaps.com'}
      userAvatar={user?.avatar || null}
      themeMode={themeMode}
      onThemeChange={setThemeMode}
      activeReservation={{
        parkingName: 'Plaza Mayor Parking',
        spot: 'A-12',
        timeRemaining: '01:45:00'
      }}
      onLogout={handleLogout}
      onViewHistory={handleViewHistory}
      onGoToSanctions={handleGoToSanctions}
      onCalendarSync={handleCalendarSync}
      onPaymentMethods={handlePaymentMethods}
      onEditProfile={handleEditProfile}
    />
  );
};

export default ProfileScreen;

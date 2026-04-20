import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import { useTheme } from '../context/ThemeContext';
import HomeScreen from '../screens/Home/HomeScreen';
import LoginScreen from '../screens/Login/LoginScreen';
import RegisterScreen from '../screens/Register/RegisterScreen';
import ParkingDetailScreen from '../screens/ParkingDetail/ParkingDetailScreen';
import ProfileScreen from '../screens/Profile/ProfileScreen';
import ReservationHistoryScreen from '../screens/ReservationHistory/ReservationHistoryScreen';
import SanctionsScreen from '../screens/Sanctions/SanctionsScreen';
import EditProfileScreen from '../screens/EditProfile/EditProfileScreen';
import CalendarSyncScreen from '../screens/CalendarSync/CalendarSyncScreen';

export type RootStackParamList = {
  Home: undefined;
  Login: undefined;
  Register: undefined;
  ParkingDetail: { parkingId: string };
  Profile: undefined;
  ReservationHistory: undefined;
  Sanctions: undefined;
  EditProfile: undefined;
  CalendarSync: undefined;
};

const Stack = createStackNavigator<RootStackParamList>();

const RootNavigator = () => {
  const { theme } = useTheme();

  return (
    <Stack.Navigator
      initialRouteName="Home"
      screenOptions={{
        headerStyle: { backgroundColor: theme.background },
        headerTintColor: theme.primary,
        headerTitleStyle: { fontWeight: 'bold' },
      }}
    >
      <Stack.Screen
        name="Home"
        component={HomeScreen}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="Login"
        component={LoginScreen}
        options={{ title: 'Log In' }}
      />
      <Stack.Screen
        name="Register"
        component={RegisterScreen}
        options={{ title: 'Create Account' }}
      />
      <Stack.Screen
        name="ParkingDetail"
        component={ParkingDetailScreen}
        options={{ title: 'Parking Details' }}
      />
      <Stack.Screen
        name="Profile"
        component={ProfileScreen}
        options={{ title: 'My Profile' }}
      />
      <Stack.Screen
        name="ReservationHistory"
        component={ReservationHistoryScreen}
        options={{ title: 'Activity History' }}
      />
      <Stack.Screen
        name="Sanctions"
        component={SanctionsScreen}
        options={{ title: 'My Sanctions' }}
      />
      <Stack.Screen
        name="EditProfile"
        component={EditProfileScreen}
        options={{ title: 'Edit Profile' }}
      />
      <Stack.Screen
        name="CalendarSync"
        component={CalendarSyncScreen}
        options={{ title: 'Smart Calendar' }}
      />
    </Stack.Navigator>
  );
};

export default RootNavigator;

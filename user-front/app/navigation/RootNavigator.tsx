import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import HomeScreen from '../screens/Home/HomeScreen';
import LoginScreen from '../screens/Login/LoginScreen';
import RegisterScreen from '../screens/Register/RegisterScreen';
import { Colors } from '../constants/Colors';
import ParkingDetailScreen from '../screens/ParkingDetail/ParkingDetailScreen';
import ProfileScreen from '../screens/Profile/ProfileScreen';
import ReservationHistoryScreen from '../screens/ReservationHistory/ReservationHistoryScreen';
import SanctionsScreen from '../screens/Sanctions/SanctionsScreen';
import EditProfileScreen from '../screens/EditProfile/EditProfileScreen';

export type RootStackParamList = {
  Home: undefined;
  Login: undefined;
  Register: undefined;
  ParkingDetail: { parkingId: string };
  Profile: undefined;
  ReservationHistory: undefined;
  Sanctions: undefined;
  EditProfile: undefined;
};

const Stack = createStackNavigator<RootStackParamList>();

const RootNavigator = () => {
  return (
    <Stack.Navigator
      initialRouteName="Home"
      screenOptions={{
        headerStyle: { backgroundColor: Colors.background },
        headerTintColor: Colors.primary, // Color de la flecha y título
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
    </Stack.Navigator>
  );
};


export default RootNavigator;

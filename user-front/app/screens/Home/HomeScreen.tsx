import React, { useEffect, useState } from 'react';
import HomeView from './HomeView';
import { useAuth } from '../../context/AuthContext';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import parkingService, { Parking } from '../../services/parkingService';

const HomeScreen = () => {
  const { userToken } = useAuth();
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();
  const [parkings, setParkings] = useState<Parking[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadParkings();
  }, []);

  const loadParkings = async () => {
    try {
      setLoading(true);
      const data = await parkingService.getAll();
      setParkings(data);
    } catch (error) {
      console.error('Error loading parkings:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleSelectParking = (id: number) => {
    navigation.navigate('ParkingDetail', { parkingId: id.toString() });
  };

  const handleGoToProfile = () => {
    if (userToken) {
      navigation.navigate('Profile');
    } else {
      navigation.navigate('Login');
    }
  };

  return (
    <HomeView 
      parkings={parkings}
      onSelectParking={handleSelectParking} 
      onGoToProfile={handleGoToProfile} 
      isLogged={!!userToken}
      isLoading={loading}
    />
  );
};

export default HomeScreen;

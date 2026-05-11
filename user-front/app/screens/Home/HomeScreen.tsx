import React, { useEffect, useState } from 'react';
import HomeView from './HomeView';
import { useAuth } from '../../context/AuthContext';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import parkingService, { Parking } from '../../services/parkingService';
import { adService, Ad } from '../../services/adService';

const HomeScreen = () => {
  const { userToken } = useAuth();
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();
  const [parkings, setParkings] = useState<Parking[]>([]);
  const [ads, setAds] = useState<Ad[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadData();
    
    const adInterval = setInterval(() => {
      loadAds();
    }, 30000);

    return () => clearInterval(adInterval);
  }, []);

  const loadData = async () => {
    setLoading(true);
    await Promise.all([loadParkings(), loadAds()]);
    setLoading(false);
  };

  const loadAds = async () => {
    try {
      const data = await adService.getActiveAds();
      setAds(data);
    } catch (error) {
      console.error('Error loading ads:', error);
    }
  };

  const loadParkings = async () => {
    try {
      const data = await parkingService.getAll();
      setParkings(data);
    } catch (error) {
      console.error('Error loading parkings:', error);
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
      ads={ads}
      onSelectParking={handleSelectParking} 
      onGoToProfile={handleGoToProfile} 
      isLogged={!!userToken}
      isLoading={loading}
    />
  );
};

export default HomeScreen;

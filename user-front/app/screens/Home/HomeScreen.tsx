import React, { useEffect, useState, useCallback } from 'react';
import HomeView from './HomeView';
import { useAuth } from '../../context/AuthContext';
import { useNavigation, useFocusEffect } from '@react-navigation/native';
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
  const [refreshing, setRefreshing] = useState(false);
  const [viewMode, setViewMode] = useState<'list' | 'map'>('map');

  const handleToggleView = () => {
    const nextMode = viewMode === 'list' ? 'map' : 'list';
    setViewMode(nextMode);
    loadParkings(); 
  };

  useFocusEffect(
    useCallback(() => {
      loadData();
    }, [])
  );


  const loadData = async () => {
    setLoading(true);
    await Promise.all([loadParkings(), loadAds()]);
    setLoading(false);
  };

  const onRefresh = async () => {
    setRefreshing(true);
    await loadData();
    setRefreshing(false);
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
      refreshing={refreshing}
      onRefresh={onRefresh}
      viewMode={viewMode}
      onToggleView={handleToggleView}
    />
  );
};

export default HomeScreen;

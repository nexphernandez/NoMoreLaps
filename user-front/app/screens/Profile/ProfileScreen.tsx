import React, { useEffect, useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import { useTheme } from '../../context/ThemeContext';
import { useNavigation, useIsFocused } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';
import ProfileView from './ProfileView';
import reservationService, { Reservation } from '../../services/reservationService';

const ProfileScreen = () => {
  const { user, logout } = useAuth();
  const { themeMode, setThemeMode } = useTheme();
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();
  const isFocused = useIsFocused();
  
  const [activeRes, setActiveRes] = useState<Reservation | null>(null);
  const [timeStr, setTimeStr] = useState<string>('30:00');

  useEffect(() => {
    if (isFocused && user?.id) {
      loadData();
    }
  }, [isFocused, user?.id]);

  useEffect(() => {
    let interval: NodeJS.Timeout;

    if (activeRes) {
      updateTimer();
      interval = setInterval(updateTimer, 1000);
    }

    function updateTimer() {
      if (!activeRes) return;
      
      const now = Date.now();
      const start = activeRes.startTime ? new Date(activeRes.startTime).getTime() : now;
      const end = activeRes.endTime ? new Date(activeRes.endTime).getTime() : now;
      
      let diff;
      
      if (now < start) {
        diff = end - start;
      } else {
        diff = end - now;
      }

      if (diff <= 0 || isNaN(diff)) {
        setTimeStr('00:00');
        if (diff < -5000) loadData(); 
      } else {
        const mins = Math.floor(diff / 60000);
        const secs = Math.floor((diff % 60000) / 1000);
        setTimeStr(`${mins}:${secs < 10 ? '0' : ''}${secs}`);
      }
    }

    return () => {
      if (interval) clearInterval(interval);
    };
  }, [activeRes]);

  const loadData = async () => {
    try {
      const reservations = await reservationService.getByUserId(user!.id);
      const now = new Date();
      
      const active = reservations
        .filter(r => {
          const s = r.state?.toUpperCase();
          return s === 'ACTIVE' || s === 'ACTIVA';
        })
        .sort((a, b) => new Date(a.startTime).getTime() - new Date(b.startTime).getTime())
        .find(r => new Date(r.endTime) > now); 

      setActiveRes(active || null);
    } catch (error) {
      console.error('Error loading profile data:', error);
    }
  };

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

  return (
    <ProfileView
      userName={user?.name || 'Guest User'}
      userEmail={user?.email || 'guest@nomorelaps.com'}
      userAvatar={user?.avatar || null}
      themeMode={themeMode}
      onThemeChange={setThemeMode}
      activeReservation={activeRes ? {
        parkingName: (activeRes as any).parkingName || 'Parking Facility',
        spot: `Spot #${activeRes.parkingSpotId}`,
        timeRemaining: timeStr
      } : null}
      onLogout={handleLogout}
      onViewHistory={handleViewHistory}
      onGoToSanctions={handleGoToSanctions}
      onCalendarSync={handleCalendarSync}
      onEditProfile={handleEditProfile}
    />
  );
};

export default ProfileScreen;

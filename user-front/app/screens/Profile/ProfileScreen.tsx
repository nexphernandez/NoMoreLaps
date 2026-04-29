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

  // TIMER EFFECT
  useEffect(() => {
    let interval: NodeJS.Timeout;

    if (activeRes) {
      updateTimer();
      interval = setInterval(updateTimer, 1000);
    }

    function updateTimer() {
      if (!activeRes) return;
      
      const now = Date.now();
      
      // FIX TIMEZONE: If server sends "2024-04-29T12:00:00", it's usually UTC.
      // JS needs a 'Z' at the end to treat it as UTC, otherwise it assumes Local.
      let dateStr = activeRes.startTime;
      if (dateStr && !dateStr.endsWith('Z') && !dateStr.includes('+')) {
          dateStr += 'Z'; 
      }

      const start = dateStr ? new Date(dateStr).getTime() : now;
      const totalWindow = 30 * 60 * 1000;
      let diff = (start + totalWindow) - now;

      if (diff <= 0 || isNaN(diff)) {
        setTimeStr('00:00');
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
      const active = reservations.find(r => {
        const s = r.state?.toUpperCase();
        return s === 'ACTIVA' || s === 'ACTIVE' || s === 'PENDING';
      });
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
      userAvatar={null}
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

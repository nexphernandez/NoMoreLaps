import React, { useState, useCallback } from 'react';
import { useFocusEffect } from '@react-navigation/native';
import ReservationHistoryView from './ReservationHistoryView';
import { useAuth } from '../../context/AuthContext';
import reservationService, { Reservation } from '../../services/reservationService';
import { ActivityIndicator, View } from 'react-native';

const ReservationHistoryScreen = () => {
  const { user } = useAuth();
  const [reservations, setReservations] = useState<Reservation[]>([]);
  const [loading, setLoading] = useState(true);

  useFocusEffect(
    useCallback(() => {
      if (user) {
        loadReservations();
      }
    }, [user])
  );

  const loadReservations = async () => {
    try {
      setLoading(true);
      const data = await reservationService.getByUserId(user!.id);
      setReservations(data);
    } catch (error) {
      console.error('Error loading history:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <ActivityIndicator size="large" color="#3B82F6" />
      </View>
    );
  }

  const handleDelete = async (id: number) => {
    try {
      await reservationService.cancel(id);
      await loadReservations();
    } catch (error) {
      console.error('Error deleting reservation:', error);
    }
  };

  return (
    <ReservationHistoryView 
      history={reservations} 
      onViewReceipt={(id) => console.log('View receipt', id)}
      onDelete={handleDelete}
    />
  );
};

export default ReservationHistoryScreen;

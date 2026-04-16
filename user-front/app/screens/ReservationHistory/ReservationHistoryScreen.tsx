import React from 'react';
import ReservationHistoryView, { HistoryItem } from './ReservationHistoryView';

const MOCK_HISTORY: HistoryItem[] = [
  { id: '1', parkingName: 'Plaza Mayor Parking', date: '12 Apr 2024', duration: '2h 15m', price: '5.20€', status: 'Completed' },
  { id: '2', parkingName: 'Parking Sol', date: '10 Apr 2024', duration: '0h 45m', price: '2.10€', status: 'Completed' },
  { id: '3', parkingName: 'Zaragoza Central', date: '05 Apr 2024', duration: '5h 00m', price: '12.00€', status: 'Cancelled' },
];

const ReservationHistoryScreen = () => {
  const handleViewReceipt = (id: string) => {
    console.log('Viewing receipt for:', id);
    // Lógica futura para mostrar PDF o modal de recibo
  };

  return (
    <ReservationHistoryView 
      history={MOCK_HISTORY} 
      onViewReceipt={handleViewReceipt}
    />
  );
};

export default ReservationHistoryScreen;

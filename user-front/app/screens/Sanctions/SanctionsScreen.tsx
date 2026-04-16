import React from 'react';
import SanctionsView from './SanctionsView';

// Datos de prueba para las sanciones
const MOCK_SANCTIONS = [
  { 
    id: '1', 
    parkingName: 'Plaza Mayor Parking', 
    date: '10 Apr 2024', 
    amount: '40.00€', 
    reason: 'Exceeded reservation time by 45 minutes.', 
    isPaid: false 
  },
  { 
    id: '2', 
    parkingName: 'Parking Sol', 
    date: '02 Mar 2024', 
    amount: '15.00€', 
    reason: 'Incorrect spot occupation.', 
    isPaid: true 
  },
];

const SanctionsScreen = () => {
  return (
    <SanctionsView sanctions={MOCK_SANCTIONS} />
  );
};

export default SanctionsScreen;

import React from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity, ScrollView } from 'react-native';
import { Colors } from '../../constants/Colors';

// Datos de prueba para las plazas (Parking Spots)
const MOCK_SPOTS = [
  { id: '1', number: 'A-01', isOccupied: false },
  { id: '2', number: 'A-02', isOccupied: true },
  { id: '3', number: 'A-03', isOccupied: false },
  { id: '4', number: 'B-01', isOccupied: false },
  { id: '5', number: 'B-02', isOccupied: true },
  { id: '6', number: 'B-03', isOccupied: false },
];

interface ParkingDetailViewProps {
  parkingName: string;
  selectedSpotId: string | null;
  onSelectSpot: (id: string) => void;
  onReserve: () => void;
}

const ParkingDetailView: React.FC<ParkingDetailViewProps> = ({ 
  parkingName, selectedSpotId, onSelectSpot, onReserve 
}) => {
  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>{parkingName}</Text>
        <Text style={styles.subtitle}>Select a spot to reserve</Text>
      </View>

      <FlatList
        data={MOCK_SPOTS}
        numColumns={3} // Diseño en cuadrícula de 3 columnas
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
        renderItem={({ item }) => (
          <TouchableOpacity 
            style={[
              styles.spotCard, 
              item.isOccupied && styles.occupiedSpot,
              selectedSpotId === item.id && styles.selectedSpot
            ]}
            onPress={() => !item.isOccupied && onSelectSpot(item.id)}
            disabled={item.isOccupied}
          >
            <Text style={[
              styles.spotNumber,
              item.isOccupied && styles.occupiedText,
              selectedSpotId === item.id && styles.selectedText
            ]}>
              {item.number}
            </Text>
            <Text style={styles.statusText}>
              {item.isOccupied ? 'Full' : 'Free'}
            </Text>
          </TouchableOpacity>
        )}
      />

      <View style={styles.footer}>
        <TouchableOpacity 
          style={[styles.reserveButton, !selectedSpotId && styles.disabledButton]} 
          onPress={onReserve}
          disabled={!selectedSpotId}
        >
          <Text style={styles.reserveButtonText}>Reserve Spot</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: Colors.lightBackground },
  header: { padding: 24, backgroundColor: Colors.background, borderBottomWidth: 1, borderBottomColor: Colors.border },
  title: { fontSize: 24, fontWeight: 'bold', color: Colors.text },
  subtitle: { fontSize: 16, color: Colors.textSecondary, marginTop: 4 },
  list: { padding: 16 },
  spotCard: {
    flex: 1,
    margin: 8,
    height: 80,
    backgroundColor: Colors.background,
    borderRadius: 12,
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: 2,
    borderColor: Colors.border,
  },
  occupiedSpot: { backgroundColor: '#FEE2E2', borderColor: Colors.danger },
  selectedSpot: { backgroundColor: Colors.primary, borderColor: Colors.primary },
  spotNumber: { fontSize: 18, fontWeight: 'bold', color: Colors.text },
  occupiedText: { color: Colors.danger },
  selectedText: { color: '#fff' },
  statusText: { fontSize: 10, color: Colors.textSecondary, marginTop: 4 },
  footer: { padding: 24, backgroundColor: Colors.background, borderTopWidth: 1, borderTopColor: Colors.border },
  reserveButton: { backgroundColor: Colors.primary, padding: 18, borderRadius: 12, alignItems: 'center' },
  disabledButton: { backgroundColor: Colors.border },
  reserveButtonText: { color: '#fff', fontSize: 16, fontWeight: 'bold' },
});

export default ParkingDetailView;

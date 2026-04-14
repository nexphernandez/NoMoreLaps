import React from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity, SafeAreaView } from 'react-native';
import { Colors } from '../../constants/Colors';

// Datos de prueba para simular el backend
const MOCK_PARKINGS = [
  { id: '1', name: 'Plaza Mayor Parking', address: 'Calle Mayor, 1', spots: 15, price: '2.50€/h' },
  { id: '2', name: 'Parking Sol', address: 'Puerta del Sol, 5', spots: 3, price: '3.00€/h' },
  { id: '3', name: 'Zaragoza Central', address: 'Av. Gran Vía, 12', spots: 45, price: '1.80€/h' },
];

interface HomeViewProps {
  onSelectParking: (id: string) => void;
  onGoToProfile: () => void;
}

const HomeView: React.FC<HomeViewProps> = ({ onSelectParking, onGoToProfile }) => {
  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.logo}>NoMoreLaps</Text>
        <TouchableOpacity onPress={onGoToProfile} style={styles.profileIcon}>
           <Text style={styles.profileText}>Sing in</Text>
        </TouchableOpacity>
      </View>

      <FlatList
        data={MOCK_PARKINGS}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
        renderItem={({ item }) => (
          <TouchableOpacity 
            style={styles.parkingCard} 
            onPress={() => onSelectParking(item.id)}
          >
            <View style={styles.info}>
              <Text style={styles.name}>{item.name}</Text>
              <Text style={styles.address}>{item.address}</Text>
              <Text style={styles.price}>{item.price}</Text>
            </View>
            <View style={[styles.badge, { backgroundColor: item.spots < 5 ? Colors.danger : Colors.success }]}>
              <Text style={styles.badgeText}>{item.spots} free</Text>
            </View>
          </TouchableOpacity>
        )}
      />
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: Colors.lightBackground },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    padding: 20,
    paddingTop: 40,
    backgroundColor: Colors.background,
    borderBottomWidth: 1,
    borderBottomColor: Colors.border,
  },
  logo: { fontSize: 22, fontWeight: 'bold', color: Colors.primary },
  list: { padding: 16 },
  parkingCard: {
    backgroundColor: Colors.background,
    borderRadius: 12,
    padding: 16,
    marginBottom: 16,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    elevation: 2,
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  info: {
    flex: 1,
  },
  name: { fontSize: 18, fontWeight: '600', color: Colors.text },
  address: { fontSize: 14, color: Colors.textSecondary, marginTop: 4 },
  price: { fontSize: 14, color: Colors.primary, fontWeight: 'bold', marginTop: 4 },
  badge: { paddingHorizontal: 12, paddingVertical: 6, borderRadius: 20 },
  badgeText: { color: '#fff', fontSize: 12, fontWeight: 'bold' },
  profileIcon: { backgroundColor: Colors.lightBackground, padding: 8, borderRadius: 20 },
  profileText: { color: Colors.textSecondary, fontSize: 12 }
});

export default HomeView;

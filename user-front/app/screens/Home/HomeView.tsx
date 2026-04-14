import React, { useState } from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity, Platform, Linking } from 'react-native';
import MapView, { Marker, PROVIDER_GOOGLE } from 'react-native-maps';
import { SafeAreaView } from 'react-native-safe-area-context';
import { Colors } from '../../constants/Colors';

const MOCK_PARKINGS = [
  { id: '1', name: 'Plaza Mayor Parking', address: 'Calle Mayor, 1', spots: 15, price: '2.50€/h', latitude: 40.4153, longitude: -3.7074 },
  { id: '2', name: 'Parking Sol', address: 'Puerta del Sol, 5', spots: 3, price: '3.00€/h', latitude: 40.4168, longitude: -3.7038 },
  { id: '3', name: 'Zaragoza Central', address: 'Av. Gran Vía, 12', spots: 45, price: '1.80€/h', latitude: 41.6488, longitude: -0.8891 },
];

interface HomeViewProps {
  onSelectParking: (id: string) => void;
  onGoToProfile: () => void;
}

const HomeView: React.FC<HomeViewProps> = ({ onSelectParking, onGoToProfile }) => {
  const [viewMode, setViewMode] = useState<'list' | 'map'>('map');
  const [selectedParking, setSelectedParking] = useState<any>(null); // Estado para la ficha inferior

  const openInGoogleMaps = (lat: number, lng: number) => {
    const url = Platform.select({
      ios: `maps://app?daddr=${lat},${lng}`,
      android: `google.navigation:q=${lat},${lng}`
    });
    Linking.openURL(url || '');
  };

  return (
    <SafeAreaView style={styles.container} edges={['top', 'bottom']}>
      {/* HEADER */}
      <View style={styles.header}>
        <Text style={styles.logo}>NoMoreLaps</Text>
        <TouchableOpacity onPress={onGoToProfile} style={styles.profileIcon}>
           <Text style={styles.profileText}>Sign in</Text>
        </TouchableOpacity>
      </View>

      <View style={styles.content}>
        {viewMode === 'list' ? (
          <FlatList
            data={MOCK_PARKINGS}
            keyExtractor={(item) => item.id}
            contentContainerStyle={styles.list}
            renderItem={({ item }) => (
              <TouchableOpacity style={styles.parkingCard} onPress={() => onSelectParking(item.id)}>
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
        ) : (
          <MapView
            provider={Platform.OS === 'android' ? PROVIDER_GOOGLE : undefined}
            style={styles.map}
            onPress={() => setSelectedParking(null)} // Cerramos ficha si pulsas el mapa
            initialRegion={{
              latitude: 40.4168,
              longitude: -3.7038,
              latitudeDelta: 0.05,
              longitudeDelta: 0.05,
            }}
          >
            {MOCK_PARKINGS.map(parking => (
              <Marker
                key={parking.id}
                coordinate={{ latitude: parking.latitude, longitude: parking.longitude }}
                pinColor={parking.spots < 5 ? Colors.danger : Colors.primary}
                onPress={(e) => {
                  e.stopPropagation(); // Evita que se cierre al pulsar el marcador
                  setSelectedParking(parking);
                }}
              />
            ))}
          </MapView>
        )}
      </View>

      {/* FICHA INFERIOR (Solo Mapa) */}
      {viewMode === 'map' && selectedParking && (
        <View style={styles.bottomCard}>
          <View style={styles.cardInfo}>
            <View style={{ flex: 1 }}>
              <Text style={styles.cardTitle}>{selectedParking.name}</Text>
              <Text style={styles.cardAddress}>{selectedParking.address}</Text>
              <Text style={styles.cardPrice}>{selectedParking.price}</Text>
            </View>
            <TouchableOpacity onPress={() => setSelectedParking(null)} style={styles.closeBtn}>
              <Text style={{ fontSize: 20 }}>✕</Text>
            </TouchableOpacity>
          </View>
          
          <View style={styles.cardButtons}>
            <TouchableOpacity 
              style={[styles.actionBtn, styles.googleBtn]}
              onPress={() => openInGoogleMaps(selectedParking.latitude, selectedParking.longitude)}
            >
              <Text style={styles.googleBtnText}>📍 Google Maps</Text>
            </TouchableOpacity>
            
            <TouchableOpacity 
              style={[styles.actionBtn, styles.reserveBtn]}
              onPress={() => onSelectParking(selectedParking.id)}
            >
              <Text style={styles.reserveBtnText}>Reserve Now</Text>
            </TouchableOpacity>
          </View>
        </View>
      )}

      {/* BOTÓN FLOTANTE MODO VISTA (Sube si hay tarjeta) */}
      <TouchableOpacity 
        style={[styles.toggleButton, selectedParking && viewMode === 'map' && { bottom: 210 }]} 
        onPress={() => setViewMode(viewMode === 'list' ? 'map' : 'list')}
      >
        <Text style={styles.toggleText}>
          {viewMode === 'list' ? '🗺️ Ver Mapa' : '📋 Ver Lista'}
        </Text>
      </TouchableOpacity>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: Colors.lightBackground },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingHorizontal: 20,
    paddingVertical: 15,
    backgroundColor: Colors.background,
    borderBottomWidth: 1,
    borderBottomColor: Colors.border,
    zIndex: 10,
  },
  logo: { fontSize: 22, fontWeight: 'bold', color: Colors.primary },
  content: { flex: 1 },
  list: { padding: 16, paddingBottom: 100 },
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
  info: { flex: 1 },
  name: { fontSize: 18, fontWeight: '600', color: Colors.text },
  address: { fontSize: 14, color: Colors.textSecondary, marginTop: 4 },
  price: { fontSize: 14, color: Colors.primary, fontWeight: 'bold', marginTop: 4 },
  badge: { paddingHorizontal: 12, paddingVertical: 6, borderRadius: 20 },
  badgeText: { color: '#fff', fontSize: 12, fontWeight: 'bold' },
  profileIcon: { backgroundColor: Colors.lightBackground, padding: 8, borderRadius: 20 },
  profileText: { color: Colors.primary, fontSize: 14, fontWeight: 'bold' },
  map: { width: '100%', height: '100%' },
  
  bottomCard: {
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
    backgroundColor: Colors.background,
    borderTopLeftRadius: 24,
    borderTopRightRadius: 24,
    padding: 24,
    paddingBottom: 40,
    elevation: 20,
    shadowColor: '#000',
    shadowOpacity: 0.2,
    shadowRadius: 10,
    zIndex: 100,
  },
  cardInfo: { flexDirection: 'row', marginBottom: 20 },
  cardTitle: { fontSize: 20, fontWeight: 'bold', color: Colors.text },
  cardAddress: { fontSize: 14, color: Colors.textSecondary, marginTop: 4 },
  cardPrice: { fontSize: 16, color: Colors.primary, fontWeight: 'bold', marginTop: 8 },
  closeBtn: { padding: 5 },
  cardButtons: { flexDirection: 'row', gap: 12 },
  actionBtn: { flex: 1, padding: 14, borderRadius: 12, alignItems: 'center' },
  googleBtn: { backgroundColor: Colors.lightBackground, borderWidth: 1, borderColor: Colors.border },
  googleBtnText: { color: Colors.text, fontWeight: 'bold' },
  reserveBtn: { backgroundColor: Colors.primary },
  reserveBtnText: { color: '#fff', fontWeight: 'bold' },

  toggleButton: {
    position: 'absolute',
    bottom: 50,
    alignSelf: 'center',
    backgroundColor: Colors.text,
    paddingHorizontal: 24,
    paddingVertical: 12,
    borderRadius: 30,
    elevation: 5,
    zIndex: 50,
  },
  toggleText: { color: '#fff', fontWeight: 'bold' },
});

export default HomeView;

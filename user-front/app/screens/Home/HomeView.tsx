import React, { useState } from 'react';
import { View, StyleSheet, FlatList, TouchableOpacity, Platform, Linking } from 'react-native';
import MapView, { Marker, PROVIDER_GOOGLE } from 'react-native-maps';
import { SafeAreaView } from 'react-native-safe-area-context';
import Typography from '../../components/Typography';
import CustomButton from '../../components/CustomButton';
import ParkingCard from '../../components/ParkingCard';
import { useTheme } from '../../context/ThemeContext';

const MOCK_PARKINGS = [
  { id: '1', name: 'Plaza Mayor Parking', address: 'Calle Mayor, 1', spots: 15, price: '2.50€/h', latitude: 40.4153, longitude: -3.7074 },
  { id: '2', name: 'Parking Sol', address: 'Puerta del Sol, 5', spots: 3, price: '3.00€/h', latitude: 40.4168, longitude: -3.7038 },
  { id: '3', name: 'Zaragoza Central', address: 'Av. Gran Vía, 12', spots: 45, price: '1.80€/h', latitude: 41.6488, longitude: -0.8891 },
];

interface HomeViewProps {
  onSelectParking: (id: string) => void;
  onGoToProfile: () => void;
  isLogged: boolean;
}

const HomeView: React.FC<HomeViewProps> = ({ onSelectParking, onGoToProfile, isLogged }) => {
  const { theme } = useTheme();
  const [viewMode, setViewMode] = useState<'list' | 'map'>('map');
  const [selectedParking, setSelectedParking] = useState<any>(null);
  const [showAdAlert, setShowAdAlert] = useState(true);

  const openInGoogleMaps = (lat: number, lng: number) => {
    const url = Platform.select({
      ios: `maps://app?daddr=${lat},${lng}`,
      android: `google.navigation:q=${lat},${lng}`
    });
    Linking.openURL(url || '');
  };

  return (
    <SafeAreaView style={[styles.container, { backgroundColor: theme.lightBackground }]} edges={['top', 'bottom']}>
      {/* AD ALERT MODAL - Contextual Ad */}
      {showAdAlert && viewMode === 'map' && (
        <View style={[styles.adAlert, { backgroundColor: theme.primary }]}>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color="#FFF" style={{ fontWeight: 'bold' }}>🎁 NEARBY OFFER</Typography>
            <Typography variant="body" color="#FFF">20% discount at 'Star Coffee' next to Sol Parking!</Typography>
          </View>
          <TouchableOpacity onPress={() => setShowAdAlert(false)} style={styles.adClose}>
            <Typography variant="h3" color="#FFF">✕</Typography>
          </TouchableOpacity>
        </View>
      )}

      {/* HEADER */}
      <View style={[styles.header, { backgroundColor: theme.background, borderBottomColor: theme.border }]}>
        <Typography variant="h2" color={theme.primary}>NoMoreLaps</Typography>
        <TouchableOpacity onPress={onGoToProfile} style={[styles.profileIcon, { backgroundColor: theme.lightBackground }]}>
           <Typography variant="label" color={theme.primary}>
             {isLogged ? 'Profile' : 'Sign in'}
           </Typography>
        </TouchableOpacity>
      </View>

      <View style={styles.content}>
        {viewMode === 'list' ? (
          <FlatList
            data={MOCK_PARKINGS}
            keyExtractor={(item) => item.id}
            contentContainerStyle={styles.list}
            ListHeaderComponent={() => (
              <TouchableOpacity style={[styles.sponsoredBanner, { backgroundColor: theme.background, borderColor: theme.primary }]}>
                <View style={styles.sponsoredBadge}>
                  <Typography variant="label" color="#FFF">SPONSORED</Typography>
                </View>
                <Typography variant="h3">Charge & Park</Typography>
                <Typography variant="caption">Free electric charging with your reservation today at selected spots.</Typography>
              </TouchableOpacity>
            )}
            renderItem={({ item }) => (
              <ParkingCard
                name={item.name}
                distance={item.address}
                availableSpots={item.spots}
                onPress={() => onSelectParking(item.id)}
              />
            )}
          />
        ) : (
          <MapView
            provider={Platform.OS === 'android' ? PROVIDER_GOOGLE : undefined}
            style={styles.map}
            onPress={() => setSelectedParking(null)}
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
                pinColor={parking.spots < 5 ? theme.danger : theme.primary}
                onPress={(e) => {
                  e.stopPropagation();
                  setSelectedParking(parking);
                }}
              />
            ))}
          </MapView>
        )}
      </View>

      {/* FICHA INFERIOR */}
      {viewMode === 'map' && selectedParking && (
        <View style={[styles.bottomCard, { backgroundColor: theme.background }]}>
          <View style={styles.cardInfo}>
            <View style={{ flex: 1 }}>
              <Typography variant="h2">{selectedParking.name}</Typography>
              <Typography variant="body" color={theme.textSecondary}>{selectedParking.address}</Typography>
              <Typography variant="h3" color={theme.primary} style={{marginTop: 4}}>{selectedParking.price}</Typography>
            </View>
            <TouchableOpacity onPress={() => setSelectedParking(null)} style={styles.closeBtn}>
              <Typography variant="h3">✕</Typography>
            </TouchableOpacity>
          </View>
          <View style={styles.cardButtons}>
            <View style={{flex:1}}>
               <CustomButton 
                 variant="outline"
                 title="Directions" 
                 onPress={() => openInGoogleMaps(selectedParking.latitude, selectedParking.longitude)} 
               />
            </View>
            <View style={{flex:1}}>
              <CustomButton 
                title="View Spots" 
                onPress={() => onSelectParking(selectedParking.id)} 
              />
            </View>
          </View>
        </View>
      )}

      {/* BOTÓN CONMUTADOR */}
      <TouchableOpacity 
        style={[styles.toggleButton, { backgroundColor: theme.text }]} 
        onPress={() => setViewMode(viewMode === 'list' ? 'map' : 'list')}
      >
        <Typography color={theme.background} style={{ fontWeight: 'bold' }}>
          {viewMode === 'list' ? '🗺️ Map View' : '📋 List View'}
        </Typography>
      </TouchableOpacity>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1 },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 20,
    paddingVertical: 15,
    borderBottomWidth: 1,
    zIndex: 10,
  },
  content: { flex: 1 },
  list: { padding: 16, paddingBottom: 100 },
  profileIcon: { paddingHorizontal: 12, paddingVertical: 6, borderRadius: 20 },
  map: { width: '100%', height: '100%' },
  bottomCard: {
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
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
  closeBtn: { padding: 5 },
  cardButtons: { flexDirection: 'row', gap: 12 },
  toggleButton: {
    position: 'absolute',
    bottom: 50,
    alignSelf: 'center',
    paddingHorizontal: 24,
    paddingVertical: 12,
    borderRadius: 30,
    elevation: 5,
    zIndex: 50,
  },
  adAlert: {
    padding: 16,
    margin: 16,
    borderRadius: 16,
    flexDirection: 'row',
    alignItems: 'center',
    position: 'absolute',
    top: 70,
    left: 0,
    right: 0,
    zIndex: 1000,
    elevation: 10,
  },
  adClose: {
    padding: 8,
    marginLeft: 8,
  },
  sponsoredBanner: {
    padding: 24,
    borderRadius: 20,
    borderWidth: 2,
    borderStyle: 'dashed',
    marginBottom: 24,
    position: 'relative',
    overflow: 'hidden',
  },
  sponsoredBadge: {
    position: 'absolute',
    top: 0,
    right: 0,
    backgroundColor: '#3B82F6',
    paddingHorizontal: 12,
    paddingVertical: 4,
    borderBottomLeftRadius: 12,
  },
});

export default HomeView;

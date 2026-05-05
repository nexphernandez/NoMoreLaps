import React, { useState } from 'react';
import { View, StyleSheet, FlatList, TouchableOpacity, Platform, Linking } from 'react-native';
import MapView, { Marker, PROVIDER_GOOGLE } from 'react-native-maps';
import { SafeAreaView } from 'react-native-safe-area-context';
import Typography from '../../components/Typography';
import CustomButton from '../../components/CustomButton';
import ParkingCard from '../../components/ParkingCard';
import { useTheme } from '../../context/ThemeContext';

import { Parking } from '../../services/parkingService';

interface HomeViewProps {
  parkings: Parking[];
  onSelectParking: (id: number) => void;
  onGoToProfile: () => void;
  isLogged: boolean;
  isLoading: boolean;
}

const HomeView: React.FC<HomeViewProps> = ({ parkings, onSelectParking, onGoToProfile, isLogged, isLoading }) => {
  const { theme } = useTheme();
  const [viewMode, setViewMode] = useState<'list' | 'map'>('map');
  const [selectedParking, setSelectedParking] = useState<Parking | null>(null);
  const [showAdAlert, setShowAdAlert] = useState(true);

  const openInGoogleMaps = (lat: number, lng: number) => {
    const url = Platform.select({
      ios: `maps://app?daddr=${lat},${lng}`,
      android: `google.navigation:q=${lat},${lng}`
    });
    Linking.openURL(url || '');
  };

  if (isLoading) {
    return (
      <SafeAreaView style={[styles.container, { backgroundColor: theme.lightBackground, justifyContent: 'center', alignItems: 'center' }]}>
        <Typography variant="h2" color={theme.primary}>Loading Parkings...</Typography>
      </SafeAreaView>
    );
  }

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
            data={parkings}
            keyExtractor={(item) => item.id.toString()}
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
                availableSpots={10} // Backend improvement: count available spots
                onPress={() => onSelectParking(item.id)}
              />
            )}
            ListEmptyComponent={() => (
              <View style={{ padding: 20, alignItems: 'center' }}>
                <Typography variant="body">No parkings found in your area.</Typography>
              </View>
            )}
          />
        ) : (
          <MapView
            provider={Platform.OS === 'android' ? PROVIDER_GOOGLE : undefined}
            style={styles.map}
            onPress={() => setSelectedParking(null)}
            initialRegion={{
              latitude: parkings.length > 0 ? parkings[0].latitude : 40.4168,
              longitude: parkings.length > 0 ? parkings[0].longitude : -3.7038,
              latitudeDelta: 0.1,
              longitudeDelta: 0.1,
            }}
          >
            {parkings.map(parking => (
              <Marker
                key={parking.id}
                coordinate={{ latitude: parking.latitude, longitude: parking.longitude }}
                pinColor={theme.primary}
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
              <Typography variant="h3" color={theme.primary} style={{marginTop: 4}}>2.00 €/h</Typography>
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

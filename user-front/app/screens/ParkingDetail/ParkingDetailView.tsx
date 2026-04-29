import React from 'react';
import { View, StyleSheet, FlatList, TouchableOpacity } from 'react-native';
import CustomButton from '../../components/CustomButton';
import Badge from '../../components/Badge';
import ScreenContainer from '../../components/ScreenContainer';
import Typography from '../../components/Typography';
import { useTheme } from '../../context/ThemeContext';

import { ParkingSpot } from '../../services/parkingService';

interface ParkingDetailViewProps {
  parkingName: string;
  address?: string;
  spots: ParkingSpot[];
  selectedSpotId: string | null;
  onSelectSpot: (id: string) => void;
  onReserve: () => void;
}

const ParkingDetailView: React.FC<ParkingDetailViewProps> = ({ 
  parkingName, address, spots, selectedSpotId, onSelectSpot, onReserve 
}) => {
  const { theme } = useTheme();

  return (
    <ScreenContainer withScroll={false} style={{padding: 0}}>
      <View style={[styles.header, { backgroundColor: theme.background, borderBottomColor: theme.border }]}>
        <Typography variant="h2">{parkingName}</Typography>
        {address && <Typography variant="body" color={theme.textSecondary}>{address}</Typography>}
        <Typography variant="caption" style={{marginTop: 4}}>Select a spot to reserve</Typography>
      </View>

      <FlatList
        data={spots}
        numColumns={3}
        keyExtractor={(item) => item.id.toString()}
        contentContainerStyle={styles.list}
        renderItem={({ item }) => {
          // In our backend: state = true means available, state = false means occupied
          const isOccupied = !item.state;
          const isSelected = selectedSpotId === item.id.toString();

          return (
            <TouchableOpacity 
              style={[
                styles.spotCard, 
                { backgroundColor: theme.background, borderColor: theme.border },
                isOccupied && { backgroundColor: '#FEE2E2', borderColor: theme.danger },
                isSelected && { backgroundColor: theme.primary, borderColor: theme.primary }
              ]}
              onPress={() => !isOccupied && onSelectSpot(item.id.toString())}
              disabled={isOccupied}
            >
              <Typography variant="h3" color={isOccupied ? theme.danger : isSelected ? '#FFF' : theme.text}>
                P-{item.number}
              </Typography>
              <View style={{ marginTop: 4 }}>
                <Badge 
                  label={isOccupied ? 'Full' : 'Free'} 
                  type={isOccupied ? 'danger' : 'success'} 
                />
              </View>
            </TouchableOpacity>
          );
        }}
        ListEmptyComponent={() => (
          <View style={{ padding: 40, alignItems: 'center' }}>
            <Typography variant="body">No spots available currently.</Typography>
          </View>
        )}
      />

      <View style={[styles.footer, { backgroundColor: theme.background, borderTopColor: theme.border }]}>
        <CustomButton
          title={selectedSpotId ? `Reserve Spot P-${spots.find(s => s.id.toString() === selectedSpotId)?.number}` : 'Select a spot'}
          onPress={onReserve}
          disabled={!selectedSpotId}
        />
      </View>
    </ScreenContainer>
  );
};

const styles = StyleSheet.create({
  header: { padding: 24, borderBottomWidth: 1 },
  list: { padding: 16 },
  spotCard: {
    flex: 1,
    margin: 8,
    height: 80,
    borderRadius: 12,
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: 2,
  },
  footer: { padding: 24, borderTopWidth: 1 },
});

export default ParkingDetailView;

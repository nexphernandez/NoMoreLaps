import React from 'react';
import { View, StyleSheet, FlatList, TouchableOpacity } from 'react-native';
import CustomButton from '../../components/CustomButton';
import Badge from '../../components/Badge';
import ScreenContainer from '../../components/ScreenContainer';
import Typography from '../../components/Typography';
import { useTheme } from '../../context/ThemeContext';

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
  const { theme } = useTheme();

  return (
    <ScreenContainer withScroll={false} style={{padding: 0}}>
      <View style={[styles.header, { backgroundColor: theme.background, borderBottomColor: theme.border }]}>
        <Typography variant="h2">{parkingName}</Typography>
        <Typography variant="caption">Select a spot to reserve</Typography>
      </View>

      <FlatList
        data={MOCK_SPOTS}
        numColumns={3}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.list}
        renderItem={({ item }) => (
          <TouchableOpacity 
            style={[
              styles.spotCard, 
              { backgroundColor: theme.background, borderColor: theme.border },
              item.isOccupied && { backgroundColor: '#FEE2E2', borderColor: theme.danger },
              selectedSpotId === item.id && { backgroundColor: theme.primary, borderColor: theme.primary }
            ]}
            onPress={() => !item.isOccupied && onSelectSpot(item.id)}
            disabled={item.isOccupied}
          >
            <Typography variant="h3" color={item.isOccupied ? theme.danger : selectedSpotId === item.id ? '#FFF' : theme.text}>
              {item.number}
            </Typography>
            <View style={{ marginTop: 4 }}>
              <Badge 
                label={item.isOccupied ? 'Full' : 'Free'} 
                type={item.isOccupied ? 'danger' : 'success'} 
              />
            </View>
          </TouchableOpacity>
        )}
      />

      <View style={[styles.footer, { backgroundColor: theme.background, borderTopColor: theme.border }]}>
        <CustomButton
          title="Reserve Spot"
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

import React from 'react';
import { View, StyleSheet, TouchableOpacity } from 'react-native';
import Typography from './Typography';
import Card from './Card';
import Badge from './Badge';
import { useTheme } from '../context/ThemeContext';

import { MaterialCommunityIcons } from '@expo/vector-icons';

interface ParkingCardProps {
  name: string;
  distance: string;
  availableSpots: number;
  onPress: () => void;
}

const ParkingCard: React.FC<ParkingCardProps> = ({ name, distance, availableSpots, onPress }) => {
  const { theme } = useTheme();

  return (
    <TouchableOpacity onPress={onPress} activeOpacity={0.7}>
      <Card style={styles.card}>
        <View style={styles.header}>
          <Typography variant="h3">{name}</Typography>
          <Badge 
            label={`${availableSpots} Spots`} 
            type={availableSpots > 5 ? 'success' : availableSpots > 0 ? 'warning' : 'danger'} 
          />
        </View>
        <View style={[styles.footer, { borderTopColor: theme.border }]}>
          <View style={{ flexDirection: 'row', alignItems: 'center' }}>
            <MaterialCommunityIcons name="map-marker" size={14} color={theme.textSecondary} style={{ marginRight: 4 }} />
            <Typography variant="caption">{distance}</Typography>
          </View>
          <Typography variant="label" color={theme.primary}>View Details →</Typography>
        </View>
      </Card>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  card: { padding: 16 },
  header: { 
    flexDirection: 'row', 
    justifyContent: 'space-between', 
    alignItems: 'center',
    marginBottom: 12
  },
  footer: { 
    flexDirection: 'row', 
    justifyContent: 'space-between', 
    alignItems: 'center',
    borderTopWidth: 1,
    paddingTop: 12
  },
});

export default ParkingCard;

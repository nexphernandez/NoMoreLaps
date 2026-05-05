import React from 'react';
import { View, StyleSheet, TouchableOpacity } from 'react-native';
import Typography from './Typography';
import Card from './Card';
import Badge from './Badge';
import { useTheme } from '../context/ThemeContext';

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
          <Typography variant="caption">📍 {distance}</Typography>
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

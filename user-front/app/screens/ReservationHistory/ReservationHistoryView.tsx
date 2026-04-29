import React from 'react';
import { View, StyleSheet, FlatList, TouchableOpacity } from 'react-native';
import Badge from '../../components/Badge';
import Card from '../../components/Card';
import Typography from '../../components/Typography';
import ScreenContainer from '../../components/ScreenContainer';
import { useTheme } from '../../context/ThemeContext';

import { Reservation } from '../../services/reservationService';

interface ReservationHistoryViewProps {
  history: Reservation[];
  onViewReceipt: (id: number) => void;
}

const ReservationHistoryView: React.FC<ReservationHistoryViewProps> = ({ history, onViewReceipt }) => {
  const { theme } = useTheme();

  return (
    <ScreenContainer withScroll={false}>
      <FlatList
        data={history}
        keyExtractor={(item) => item.id?.toString() || Math.random().toString()}
        contentContainerStyle={styles.listContent}
        ListHeaderComponent={() => (
          <View style={styles.header}>
            <Typography variant="h2">Your Activity</Typography>
            <Typography variant="caption">Below you can find your past parking history.</Typography>
          </View>
        )}
        renderItem={({ item }) => (
          <Card>
            <View style={styles.cardHeader}>
              <Typography variant="h3">{(item as any).parkingName || `Reservation #${item.id}`}</Typography>
              <Badge 
                label={item.state || 'UNKNOWN'} 
                type={
                  item.state === 'ACTIVE' ? 'success' : 
                  item.state === 'COMPLETED' ? 'success' : 
                  item.state === 'SANCTIONED' ? 'danger' : 'danger'
                } 
              />
            </View>

            <View style={styles.cardBody}>
              <View style={styles.detailItem}>
                <Typography variant="label">Start</Typography>
                <Typography variant="body" style={{fontWeight: '600'}}>{new Date(item.startTime).toLocaleDateString()}</Typography>
              </View>
              <View style={styles.detailItem}>
                <Typography variant="label">Status</Typography>
                <Typography variant="body" style={{fontWeight: '600'}}>{item.state}</Typography>
              </View>
              <View style={styles.detailItem}>
                <Typography variant="label">Cost</Typography>
                <Typography variant="body" style={{fontWeight: '600'}}>{item.price}€</Typography>
              </View>
            </View>
            
            <TouchableOpacity style={[styles.receiptBtn, { borderTopColor: theme.border }]} onPress={() => item.id && onViewReceipt(item.id)}>
              <Typography variant="label" color={theme.primary} style={{textAlign: 'center', paddingTop: 16}}>View Details</Typography>
            </TouchableOpacity>
          </Card>
        )}
      />
    </ScreenContainer>
  );
};

const styles = StyleSheet.create({
  listContent: { paddingBottom: 20 },
  header: { marginBottom: 24, marginTop: 10 },
  cardHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 16 },
  cardBody: { flexDirection: 'row', justifyContent: 'space-between', marginBottom: 20 },
  detailItem: { flex: 1 },
  receiptBtn: {
    borderTopWidth: 1,
  },
});

export default ReservationHistoryView;

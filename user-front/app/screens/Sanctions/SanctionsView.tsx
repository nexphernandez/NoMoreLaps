import React from 'react';
import { View, StyleSheet, FlatList } from 'react-native';
import Badge from '../../components/Badge';
import Card from '../../components/Card';
import Typography from '../../components/Typography';
import ScreenContainer from '../../components/ScreenContainer';
import { useTheme } from '../../context/ThemeContext';

import { Sanction } from '../../services/sanctionService';
import CustomButton from '../../components/CustomButton';

interface SanctionsViewProps {
  sanctions: Sanction[];
  onPaySanction: (id: number) => void;
}

const SanctionsView: React.FC<SanctionsViewProps> = ({ sanctions, onPaySanction }) => {
  const { theme } = useTheme();

  return (
    <ScreenContainer withScroll={false}>
      <FlatList
        data={sanctions}
        keyExtractor={(item) => item.id.toString()}
        contentContainerStyle={styles.listContent}
        ListHeaderComponent={() => (
          <View style={styles.header}>
            <Typography variant="h2">My Sanctions</Typography>
            <Typography variant="caption">Traffic violations and parking overstays.</Typography>
          </View>
        )}
        renderItem={({ item }) => (
          <Card>
            <View style={styles.cardHeader}>
              <Typography variant="h3">Sanction #{item.id}</Typography>
              <Badge 
                label={item.paid ? 'Paid' : 'Unpaid'} 
                type={item.paid ? 'success' : 'danger'} 
              />
            </View>

            <View style={styles.cardBody}>
              <View style={styles.detailItem}>
                <Typography variant="label">Reason</Typography>
                <Typography variant="body" style={{fontWeight: '500'}}>{item.reason}</Typography>
              </View>
              <View style={styles.row}>
                <View style={styles.detailItem}>
                  <Typography variant="label">Date</Typography>
                  <Typography variant="body" style={{fontWeight: '600'}}>{new Date(item.arrivalTime).toLocaleDateString()}</Typography>
                </View>
                <View style={styles.amountContainer}>
                  <Typography variant="label">Fine Amount</Typography>
                  <Typography variant="h2" color={theme.danger}>{item.amount}€</Typography>
                </View>
              </View>
              
              {!item.paid && (
                <View style={{marginTop: 16}}>
                  <CustomButton 
                    title="Pay Fine" 
                    onPress={() => onPaySanction(item.id)}
                    variant="outline"
                  />
                </View>
              )}
            </View>
          </Card>
        )}
        ListEmptyComponent={() => (
          <View style={styles.emptyContainer}>
            <Typography style={{ fontSize: 60, marginBottom: 20 }}>✅</Typography>
            <Typography variant="h2">You're all clear!</Typography>
            <Typography variant="caption" style={{textAlign: 'center', marginTop: 8}}>You don't have any active sanctions.</Typography>
          </View>
        )}
      />
    </ScreenContainer>
  );
};

const styles = StyleSheet.create({
  listContent: { paddingBottom: 20 },
  header: { marginBottom: 24 },
  cardHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 12 },
  cardBody: { gap: 12 },
  detailItem: { flex: 1 },
  row: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'flex-end', marginTop: 8 },
  amountContainer: { alignItems: 'flex-end' },
  emptyContainer: { alignItems: 'center', marginTop: 100 },
});

export default SanctionsView;

import React from 'react';
import { View, Text, StyleSheet, FlatList } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { Colors } from '../../constants/Colors';

interface SanctionItem {
  id: string;
  parkingName: string;
  date: string;
  amount: string;
  reason: string;
  isPaid: boolean;
}

interface SanctionsViewProps {
  sanctions: SanctionItem[];
}

const SanctionsView: React.FC<SanctionsViewProps> = ({ sanctions }) => {
  return (
    <SafeAreaView style={styles.container} edges={['bottom']}>
      <FlatList
        data={sanctions}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.listContent}
        ListHeaderComponent={() => (
          <View style={styles.header}>
            <Text style={styles.title}>My Sanctions</Text>
            <Text style={styles.subtitle}>Traffic violations and parking overstays.</Text>
          </View>
        )}
        renderItem={({ item }) => (
          <View style={styles.card}>
            <View style={styles.cardHeader}>
              <Text style={styles.parkingName}>{item.parkingName}</Text>
              <View style={[
                styles.statusBadge, 
                { backgroundColor: item.isPaid ? '#DCFCE7' : '#FFE4E6' }
              ]}>
                <Text style={[
                  styles.statusText, 
                  { color: item.isPaid ? '#166534' : '#E11D48' }
                ]}>{item.isPaid ? 'Paid' : 'Unpaid'}</Text>
              </View>
            </View>

            <View style={styles.cardBody}>
              <View style={styles.detailItem}>
                <Text style={styles.detailLabel}>Reason</Text>
                <Text style={styles.reasonText}>{item.reason}</Text>
              </View>
              <View style={styles.row}>
                <View style={styles.detailItem}>
                  <Text style={styles.detailLabel}>Date</Text>
                  <Text style={styles.detailValue}>{item.date}</Text>
                </View>
                <View style={styles.amountContainer}>
                  <Text style={styles.amountLabel}>Fine Amount</Text>
                  <Text style={styles.amountValue}>{item.amount}</Text>
                </View>
              </View>
            </View>
          </View>
        )}
        ListEmptyComponent={() => (
          <View style={styles.emptyContainer}>
            <Text style={styles.emptyIcon}>✅</Text>
            <Text style={styles.emptyTitle}>You're all clear!</Text>
            <Text style={styles.emptySubtitle}>You don't have any active sanctions.</Text>
          </View>
        )}
      />
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: Colors.lightBackground },
  listContent: { padding: 20 },
  header: { marginBottom: 24 },
  title: { fontSize: 28, fontWeight: 'bold', color: Colors.text },
  subtitle: { fontSize: 14, color: Colors.textSecondary, marginTop: 4 },
  
  card: {
    backgroundColor: Colors.background,
    borderRadius: 16,
    padding: 20,
    marginBottom: 16,
    borderWidth: 1,
    borderColor: Colors.border,
    elevation: 2,
    shadowColor: '#000',
    shadowOpacity: 0.05,
    shadowRadius: 5,
  },
  cardHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 12 },
  parkingName: { fontSize: 18, fontWeight: 'bold', color: Colors.text },
  statusBadge: { paddingHorizontal: 10, paddingVertical: 4, borderRadius: 12 },
  statusText: { fontSize: 11, fontWeight: 'bold', textTransform: 'uppercase' },
  
  cardBody: { gap: 12 },
  detailItem: { flex: 1 },
  detailLabel: { fontSize: 11, color: Colors.textSecondary, textTransform: 'uppercase', marginBottom: 4 },
  reasonText: { fontSize: 15, color: Colors.text, fontWeight: '500' },
  
  row: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'flex-end', marginTop: 8 },
  detailValue: { fontSize: 14, fontWeight: '600', color: Colors.textSecondary },
  
  amountContainer: { alignItems: 'flex-end' },
  amountLabel: { fontSize: 10, color: Colors.textSecondary, textTransform: 'uppercase', marginBottom: 2 },
  amountValue: { fontSize: 20, fontWeight: 'bold', color: Colors.danger },

  emptyContainer: { alignItems: 'center', marginTop: 100 },
  emptyIcon: { fontSize: 60, marginBottom: 20 },
  emptyTitle: { fontSize: 20, fontWeight: 'bold', color: Colors.text },
  emptySubtitle: { fontSize: 14, color: Colors.textSecondary, marginTop: 8, textAlign: 'center' },
});

export default SanctionsView;

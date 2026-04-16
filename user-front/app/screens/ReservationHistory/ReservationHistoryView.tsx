import React from 'react';
import { View, Text, StyleSheet, FlatList, TouchableOpacity } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import { Colors } from '../../constants/Colors'; 

export interface HistoryItem {
  id: string;
  parkingName: string;
  date: string;
  duration: string;
  price: string;
  status: 'Completed' | 'Cancelled';
}

interface ReservationHistoryViewProps {
  history: HistoryItem[];
  onViewReceipt: (id: string) => void;
}

const ReservationHistoryView: React.FC<ReservationHistoryViewProps> = ({ history, onViewReceipt }) => {
  return (
    <SafeAreaView style={styles.container} edges={['bottom']}>
      <FlatList
        data={history}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.listContent}
        ListHeaderComponent={() => (
          <View style={styles.header}>
            <Text style={styles.title}>Your Activity</Text>
            <Text style={styles.subtitle}>Below you can find your past parking history.</Text>
          </View>
        )}
        renderItem={({ item }) => (
          <View style={styles.card}>
            <View style={styles.cardHeader}>
              <Text style={styles.parkingName}>{item.parkingName}</Text>
              <View style={[
                styles.statusBadge, 
                { backgroundColor: item.status === 'Completed' ? '#DCFCE7' : '#FEE2E2' }
              ]}>
                <Text style={[
                  styles.statusText, 
                  { color: item.status === 'Completed' ? '#166534' : '#991B1B' }
                ]}>{item.status}</Text>
              </View>
            </View>

            <View style={styles.cardBody}>
              <View style={styles.detailItem}>
                <Text style={styles.detailLabel}>Date</Text>
                <Text style={styles.detailValue}>{item.date}</Text>
              </View>
              <View style={styles.detailItem}>
                <Text style={styles.detailLabel}>Duration</Text>
                <Text style={styles.detailValue}>{item.duration}</Text>
              </View>
              <View style={styles.detailItem}>
                <Text style={styles.detailLabel}>Cost</Text>
                <Text style={styles.detailValue}>{item.price}</Text>
              </View>
            </View>
            
            <TouchableOpacity style={styles.receiptBtn} onPress={() => onViewReceipt(item.id)}>
              <Text style={styles.receiptBtnText}>View Receipt</Text>
            </TouchableOpacity>
          </View>
        )}
      />
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: Colors.lightBackground },
  listContent: { padding: 20 },
  header: { marginBottom: 24, marginTop: 10 },
  title: { fontSize: 28, fontWeight: 'bold', color: Colors.text },
  subtitle: { fontSize: 14, color: Colors.textSecondary, marginTop: 4 },
  
  card: {
    backgroundColor: Colors.background,
    borderRadius: 16,
    padding: 20,
    marginBottom: 16,
    borderWidth: 1,
    borderColor: Colors.border,
    elevation: 3,
    shadowColor: '#000',
    shadowOpacity: 0.1,
    shadowRadius: 5,
  },
  cardHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 16 },
  parkingName: { fontSize: 18, fontWeight: 'bold', color: Colors.text },
  statusBadge: { paddingHorizontal: 10, paddingVertical: 4, borderRadius: 12 },
  statusText: { fontSize: 11, fontWeight: 'bold', textTransform: 'uppercase' },
  
  cardBody: { flexDirection: 'row', justifyContent: 'space-between', marginBottom: 20 },
  detailItem: { flex: 1 },
  detailLabel: { fontSize: 11, color: Colors.textSecondary, textTransform: 'uppercase', marginBottom: 4 },
  detailValue: { fontSize: 14, fontWeight: '600', color: Colors.text },
  
  receiptBtn: {
    borderTopWidth: 1,
    borderTopColor: Colors.border,
    paddingTop: 16,
    alignItems: 'center',
  },
  receiptBtnText: { color: Colors.primary, fontWeight: 'bold', fontSize: 14 },
});

export default ReservationHistoryView;

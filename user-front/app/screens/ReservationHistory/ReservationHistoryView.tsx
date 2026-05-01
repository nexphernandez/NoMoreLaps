import { Modal, View, StyleSheet, FlatList, TouchableOpacity, ScrollView } from 'react-native';
import CustomButton from '../../components/CustomButton';
import Badge from '../../components/Badge';
import Card from '../../components/Card';
import Typography from '../../components/Typography';
import ScreenContainer from '../../components/ScreenContainer';
import { useTheme } from '../../context/ThemeContext';

import { Reservation } from '../../services/reservationService';
import React from 'react';

interface ReservationHistoryViewProps {
  history: Reservation[];
  onViewReceipt: (id: number) => void;
  onDelete: (id: number) => void;
}

const ReservationHistoryView: React.FC<ReservationHistoryViewProps> = ({ history, onViewReceipt, onDelete }) => {
  const { theme } = useTheme();
  const [selectedRes, setSelectedRes] = React.useState<Reservation | null>(null);

  const handleOpenDetails = (res: Reservation) => {
    setSelectedRes(res);
  };

  const handleClose = () => {
    setSelectedRes(null);
  };

  const handleDelete = () => {
    if (selectedRes?.id) {
      onDelete(selectedRes.id);
      handleClose();
    }
  };

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
            
            <TouchableOpacity 
              style={[styles.receiptBtn, { borderTopColor: theme.border }]} 
              onPress={() => handleOpenDetails(item)}
            >
              <Typography variant="label" color={theme.primary} style={{textAlign: 'center', paddingTop: 16}}>View Details</Typography>
            </TouchableOpacity>
          </Card>
        )}
      />

      {/* DETALLES MODAL */}
      <Modal
        visible={!!selectedRes}
        animationType="slide"
        transparent={true}
        onRequestClose={handleClose}
      >
        <View style={styles.modalOverlay}>
          <View style={[styles.modalContent, { backgroundColor: theme.background }]}>
            <View style={styles.modalHeader}>
              <Typography variant="h2">Reservation Details</Typography>
              <TouchableOpacity onPress={handleClose}>
                <Typography variant="h3">✕</Typography>
              </TouchableOpacity>
            </View>

            {selectedRes && (
              <ScrollView>
                <View style={styles.modalBody}>
                  <View style={styles.modalRow}>
                    <Typography variant="label">Parking</Typography>
                    <Typography variant="h3">{(selectedRes as any).parkingName || 'N/A'}</Typography>
                  </View>
                  <View style={styles.modalRow}>
                    <Typography variant="label">Date</Typography>
                    <Typography variant="body">{new Date(selectedRes.startTime).toLocaleDateString()}</Typography>
                  </View>
                  <View style={styles.modalRow}>
                    <Typography variant="label">Time</Typography>
                    <Typography variant="body">
                      {new Date(selectedRes.startTime).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})} - 
                      {new Date(selectedRes.endTime).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}
                    </Typography>
                  </View>
                  <View style={styles.modalRow}>
                    <Typography variant="label">Status</Typography>
                    <Badge label={selectedRes.state || ''} type={selectedRes.state === 'ACTIVE' ? 'success' : 'danger'} />
                  </View>
                  <View style={styles.modalRow}>
                    <Typography variant="label">Total Paid</Typography>
                    <Typography variant="h2" color={theme.primary}>{selectedRes.price}€</Typography>
                  </View>
                </View>

                {selectedRes.state === 'ACTIVE' && (
                  <View style={styles.modalFooter}>
                    <CustomButton 
                      title="Cancel Reservation" 
                      variant="danger" 
                      onPress={handleDelete}
                    />
                    <Typography variant="caption" style={{textAlign: 'center', marginTop: 8}}>
                      Note: Cancellation might incur a small fee if done too late.
                    </Typography>
                  </View>
                )}
              </ScrollView>
            )}
          </View>
        </View>
      </Modal>
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
  modalOverlay: {
    flex: 1,
    backgroundColor: 'rgba(0,0,0,0.5)',
    justifyContent: 'flex-end',
  },
  modalContent: {
    borderTopLeftRadius: 30,
    borderTopRightRadius: 30,
    padding: 24,
    maxHeight: '80%',
  },
  modalHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 24,
  },
  modalBody: {
    gap: 16,
    marginBottom: 32,
  },
  modalRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingVertical: 8,
  },
  modalFooter: {
    marginTop: 10,
    paddingBottom: 20,
  },
});

export default ReservationHistoryView;

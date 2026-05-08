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
                label={
                  item.state === 'PENDING_SYNC' ? 'WAITING CONNECTION' : 
                  item.state === 'SYNC_ERROR' ? 'SYNC ERROR' :
                  item.paid ? 'PAID' : 
                  (item.state || 'UNKNOWN')
                } 
                type={
                  item.state === 'PENDING_SYNC' ? 'warning' :
                  item.state === 'SYNC_ERROR' ? 'danger' :
                  item.state === 'ACTIVE' ? 'success' : 
                  item.state === 'COMPLETED' ? 'success' : 'danger'
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
                <Typography variant="body" style={{fontWeight: '600'}}>
                  {item.paid ? 'PAID' : item.state}
                </Typography>
              </View>
              <View style={styles.detailItem}>
                <Typography variant="label">Cost</Typography>
                <View style={{ flexDirection: 'row', alignItems: 'baseline' }}>
                  <Typography variant="body" style={{fontWeight: '600'}}>{item.price}€</Typography>
                  {item.sanctionPrice && item.sanctionPrice > 0 ? (
                    <Typography variant="caption" color={theme.danger} style={{marginLeft: 4}}>
                      (inc. {item.sanctionPrice}€ fine)
                    </Typography>
                  ) : null}
                </View>
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
                    <Badge 
                      label={
                        selectedRes.state === 'PENDING_SYNC' ? 'WAITING CONNECTION' : 
                        selectedRes.state === 'SYNC_ERROR' ? 'SYNC ERROR' :
                        selectedRes.paid ? 'PAID' :
                        (selectedRes.state || '')
                      } 
                      type={
                        selectedRes.state === 'PENDING_SYNC' ? 'warning' : 
                        selectedRes.state === 'SYNC_ERROR' ? 'danger' :
                        (selectedRes.state === 'ACTIVE' ? 'success' : 'danger')
                      } 
                    />
                  </View>
                  <View style={styles.modalRow}>
                    <Typography variant="label">Payment</Typography>
                    <Badge 
                      label={selectedRes.paid ? 'PAID' : 'PENDING'} 
                      type={selectedRes.paid ? 'success' : 'warning'} 
                    />
                  </View>
                  <View style={styles.modalRow}>
                    <Typography variant="label">Base Price</Typography>
                    <Typography variant="body">{selectedRes.basePrice || selectedRes.price}€</Typography>
                  </View>
                  {selectedRes.sanctionPrice && selectedRes.sanctionPrice > 0 ? (
                    <View style={styles.modalRow}>
                      <Typography variant="label" color={theme.danger}>Sanctions</Typography>
                      <Typography variant="body" color={theme.danger}>+{selectedRes.sanctionPrice}€</Typography>
                    </View>
                  ) : null}
                  <View style={styles.modalRow}>
                    <Typography variant="label">Total Cost</Typography>
                    <Typography variant="h2" color={theme.primary}>{selectedRes.price || '0'}€</Typography>
                  </View>
                  
                  {selectedRes.state === 'PENDING_SYNC' && (
                    <View style={{ backgroundColor: '#FFFBEB', padding: 12, borderRadius: 8, marginTop: 8 }}>
                       <Typography variant="caption" color="#92400E">
                         This reservation is stored on your device. It will be sent to the server as soon as you have internet connection.
                       </Typography>
                    </View>
                  )}

                  {selectedRes.state === 'SYNC_ERROR' && (
                    <View style={{ backgroundColor: '#FEF2F2', padding: 12, borderRadius: 8, marginTop: 8 }}>
                       <Typography variant="caption" color={theme.danger}>
                         There was an error syncing this reservation with the server. This could be due to an expired session. Please try logging in again or contact support.
                       </Typography>
                    </View>
                  )}
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
  listContent: { padding: 24, paddingBottom: 60 },
  header: { marginBottom: 32, marginTop: 8 },
  cardHeader: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 20 },
  cardBody: { flexDirection: 'row', justifyContent: 'space-between', marginBottom: 24 },
  detailItem: { flex: 1 },
  receiptBtn: {
    borderTopWidth: 1,
    paddingTop: 4,
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

import React from 'react';
import { View, StyleSheet, FlatList, TouchableOpacity } from 'react-native';
import Typography from '../../components/Typography';
import Card from '../../components/Card';
import ScreenContainer from '../../components/ScreenContainer';
import { useTheme } from '../../context/ThemeContext';
import CustomButton from '../../components/CustomButton';
import Badge from '../../components/Badge';

export interface PaymentMethod {
  id: string;
  brand: 'Visa' | 'Mastercard' | 'Paypal';
  last4: string;
  expiry: string;
  isDefault: boolean;
}

interface PaymentMethodsViewProps {
  methods: PaymentMethod[];
  onAddMethod: () => void;
  onDeleteMethod: (id: string) => void;
  onSetDefault: (id: string) => void;
}

const PaymentMethodsView: React.FC<PaymentMethodsViewProps> = ({ methods, onAddMethod, onDeleteMethod, onSetDefault }) => {
  const { theme } = useTheme();

  return (
    <ScreenContainer withScroll={false}>
      <View style={styles.header}>
        <Typography variant="h2">My Wallet</Typography>
        <Typography variant="caption">Manage your cards and preferred payment methods.</Typography>
      </View>

      <FlatList
        data={methods}
        keyExtractor={(item) => item.id}
        contentContainerStyle={styles.listContent}
        renderItem={({ item }) => (
          <Card style={StyleSheet.flatten([
            styles.card, 
            { backgroundColor: item.brand === 'Visa' ? '#1A1A1A' : item.brand === 'Paypal' ? '#003087' : '#DC2626' }
          ])}>
            <View style={styles.cardHeader}>
              <Typography variant="h3" color="#FFF">{item.brand}</Typography>
              {item.isDefault && <Badge label="Default" type="success" />}
            </View>
            
            <View style={styles.cardInfo}>
              <Typography variant="h2" color="#FFF">**** **** **** {item.last4}</Typography>
            </View>

            <View style={styles.cardFooter}>
              <View>
                <Typography variant="label" color="rgba(255,255,255,0.6)">EXPIRY DATE</Typography>
                <Typography variant="body" color="#FFF">{item.expiry}</Typography>
              </View>
              <TouchableOpacity onPress={() => onDeleteMethod(item.id)}>
                <Typography variant="label" color="#FECACA">Remove</Typography>
              </TouchableOpacity>
            </View>
          </Card>
        )}
        ListEmptyComponent={() => (
          <View style={styles.emptyContainer}>
            <Typography style={{ fontSize: 60, marginBottom: 20 }}>💳</Typography>
            <Typography variant="h3">No payment methods yet</Typography>
            <Typography variant="caption" style={{ textAlign: 'center', marginTop: 8 }}>
              Add a card to enable automatic payments for your reservations and potential sanctions.
            </Typography>
          </View>
        )}
      />

      <View style={styles.footer}>
        <CustomButton title="Add New Card" onPress={onAddMethod} />
      </View>
    </ScreenContainer>
  );
};

const styles = StyleSheet.create({
  header: { marginBottom: 24, marginTop: 10 },
  listContent: { paddingBottom: 20 },
  card: {
    padding: 24,
    height: 200,
    borderRadius: 20,
    marginBottom: 16,
    justifyContent: 'space-between',
    elevation: 8,
    shadowColor: '#000',
    shadowOpacity: 0.3,
    shadowRadius: 10,
    shadowOffset: { width: 0, height: 5 },
  },
  cardHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  cardInfo: {
    marginTop: 20,
  },
  cardFooter: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'flex-end',
  },
  emptyContainer: {
    alignItems: 'center',
    justifyContent: 'center',
    padding: 40,
    marginTop: 60,
  },
  footer: {
    paddingVertical: 20,
  },
});

export default PaymentMethodsView;

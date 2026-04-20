import React from 'react';
import { View, StyleSheet, TouchableOpacity } from 'react-native';
import Typography from '../../components/Typography';
import Card from '../../components/Card';
import ScreenContainer from '../../components/ScreenContainer';
import { useTheme } from '../../context/ThemeContext';
import CustomButton from '../../components/CustomButton';
import Divider from '../../components/Divider';
import { PaymentMethod } from '../../context/PaymentContext';

export interface ReservationSummary {
  parkingName: string;
  spot: string;
  price: string;
  startTime: string;
  duration: string;
}

interface ReservationConfirmViewProps {
  summary: ReservationSummary;
  selectedCard: PaymentMethod | null;
  onConfirm: () => void;
  onCancel: () => void;
  onChangeCard: () => void;
}

const ReservationConfirmView: React.FC<ReservationConfirmViewProps> = ({ 
  summary, selectedCard, onConfirm, onCancel, onChangeCard 
}) => {
  const { theme } = useTheme();

  return (
    <ScreenContainer>
      <View style={styles.header}>
        <Typography variant="h2">Confirmar Reserva</Typography>
        <Typography variant="caption">Por favor, revisa los detalles antes de confirmar.</Typography>
      </View>

      <Card style={styles.summaryCard}>
        <View style={styles.section}>
          <Typography variant="label" color={theme.textSecondary}>PARKING</Typography>
          <Typography variant="h3">{summary.parkingName}</Typography>
        </View>

        <Divider marginVertical={16} />

        <TouchableOpacity style={styles.paymentRow} onPress={onChangeCard}>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color={theme.textSecondary}>MÉTODO DE PAGO</Typography>
            {selectedCard ? (
              <Typography variant="h3">💳 {selectedCard.brand} **** {selectedCard.last4}</Typography>
            ) : (
              <Typography variant="h3" color={theme.danger}>Sin tarjeta vinculada</Typography>
            )}
          </View>
          <Typography variant="label" color={theme.primary} style={{ fontWeight: 'bold' }}>CAMBIAR</Typography>
        </TouchableOpacity>
        
        <Divider marginVertical={16} />

        <View style={styles.row}>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color={theme.textSecondary}>PLAZA</Typography>
            <Typography variant="h3">{summary.spot}</Typography>
          </View>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color={theme.textSecondary}>PRECIO</Typography>
            <Typography variant="h3" color={theme.success}>{summary.price}</Typography>
          </View>
        </View>

        <Divider marginVertical={16} />

        <View style={styles.row}>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color={theme.textSecondary}>INICIO</Typography>
            <Typography variant="h3">{summary.startTime}</Typography>
          </View>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color={theme.textSecondary}>DURACIÓN</Typography>
            <Typography variant="h3">{summary.duration}</Typography>
          </View>
        </View>
      </Card>

      <View style={styles.disclaimer}>
        <Typography variant="caption" style={{ textAlign: 'center' }}>
          Al confirmar, aceptas el pago del coste estimado. Se podrán aplicar sanciones si excedes el tiempo de salida por más de 10 minutos.
        </Typography>
      </View>

      <View style={styles.footer}>
        <CustomButton title="Confirmar y Reservar" onPress={onConfirm} style={{ marginBottom: 12 }} />
        <TouchableOpacity onPress={onCancel} style={styles.cancelBtn}>
          <Typography variant="label" color={theme.danger}>Cancelar</Typography>
        </TouchableOpacity>
      </View>
    </ScreenContainer>
  );
};

const styles = StyleSheet.create({
  header: { marginBottom: 24, marginTop: 10 },
  summaryCard: { padding: 24, borderRadius: 20 },
  section: { marginBottom: 4 },
  row: { flexDirection: 'row' },
  paymentRow: { 
    flexDirection: 'row', 
    alignItems: 'center',
    paddingVertical: 4
  },
  disclaimer: { marginTop: 24, paddingHorizontal: 20 },
  footer: { marginTop: 40 },
  cancelBtn: { alignSelf: 'center', padding: 12 },
});

export default ReservationConfirmView;

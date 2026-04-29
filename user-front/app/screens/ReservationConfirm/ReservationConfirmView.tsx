import React from 'react';
import { View, StyleSheet, TouchableOpacity } from 'react-native';
import Typography from '../../components/Typography';
import Card from '../../components/Card';
import ScreenContainer from '../../components/ScreenContainer';
import { useTheme } from '../../context/ThemeContext';
import CustomButton from '../../components/CustomButton';
import Divider from '../../components/Divider';

export interface ReservationSummary {
  parkingName: string;
  spot: string;
  price: string;
  startTime: string;
  endTime: string;
  date: string;
  duration: string;
  sanctionPolicy: string;
  hasSanction: boolean;
}

interface ReservationConfirmViewProps {
  summary: ReservationSummary;
  onConfirm: () => void;
  onCancel: () => void;
}

const ReservationConfirmView: React.FC<ReservationConfirmViewProps> = ({ 
  summary, onConfirm, onCancel 
}) => {
  const { theme } = useTheme();

  return (
    <ScreenContainer withScroll>
      <View style={styles.header}>
        <Typography variant="h2">Confirmar Reserva</Typography>
        <Typography variant="caption" color={theme.textSecondary}>
          Revisa los detalles de tu estancia antes de confirmar.
        </Typography>
      </View>

      <Card style={styles.summaryCard}>
        <View style={styles.section}>
          <Typography variant="label" color={theme.textSecondary}>PARKING</Typography>
          <Typography variant="h3">{summary.parkingName}</Typography>
        </View>
        
        <Divider marginVertical={16} />

        <View style={styles.row}>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color={theme.textSecondary}>PLAZA</Typography>
            <Typography variant="h2" color={theme.primary}>P-{summary.spot}</Typography>
          </View>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color={theme.textSecondary}>PRECIO TOTAL</Typography>
            <Typography variant="h2" color={theme.success}>{summary.price}</Typography>
          </View>
        </View>

        <Divider marginVertical={16} />

        <View style={styles.row}>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color={theme.textSecondary}>DÍA</Typography>
            <Typography variant="h3">{summary.date}</Typography>
          </View>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color={theme.textSecondary}>DURACIÓN</Typography>
            <Typography variant="h3">{summary.duration}</Typography>
          </View>
        </View>

        <Divider marginVertical={16} />

        <View style={styles.row}>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color={theme.textSecondary}>ENTRADA</Typography>
            <Typography variant="h3" color={theme.text}>{summary.startTime}</Typography>
          </View>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color={theme.textSecondary}>SALIDA (ESTIMADA)</Typography>
            <Typography variant="h3" color={theme.text}>{summary.endTime}</Typography>
          </View>
        </View>
      </Card>

      <View style={styles.disclaimer}>
        {summary.hasSanction && (
          <View style={[styles.warningBox, { backgroundColor: theme.danger + '10', borderColor: theme.danger }]}>
            <Typography variant="label" color={theme.danger} style={{marginBottom: 4}}>⚠️ POLÍTICA DE RETRASO</Typography>
            <Typography variant="caption" style={{ textAlign: 'center' }}>
              Se aplicará una sanción de <Typography variant="caption" style={{fontWeight: 'bold'}}>{summary.sanctionPolicy}</Typography> por retraso.
            </Typography>
          </View>
        )}
        <Typography variant="caption" style={{ textAlign: 'center', marginTop: 16, opacity: 0.6 }}>
          Al confirmar, aceptas las condiciones de uso del parking. El pago se realizará directamente en el local.
        </Typography>
      </View>

      <View style={styles.footer}>
        <CustomButton title="Confirmar y Reservar" onPress={onConfirm} style={{ marginBottom: 12 }} />
        <TouchableOpacity onPress={onCancel} style={styles.cancelBtn}>
          <Typography variant="label" color={theme.danger}>Cancelar y volver</Typography>
        </TouchableOpacity>
      </View>
    </ScreenContainer>
  );
};

const styles = StyleSheet.create({
  header: { marginBottom: 32, marginTop: 10 },
  summaryCard: { 
    padding: 24, 
    borderRadius: 24,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 10 },
    shadowOpacity: 0.1,
    shadowRadius: 20,
    elevation: 5
  },
  section: { marginBottom: 4 },
  row: { flexDirection: 'row', alignItems: 'center' },
  warningBox: {
    padding: 16,
    borderRadius: 16,
    borderWidth: 1,
    alignItems: 'center',
    borderStyle: 'dashed'
  },
  disclaimer: { marginTop: 24, paddingHorizontal: 20 },
  footer: { marginTop: 40, marginBottom: 24 },
  cancelBtn: { alignSelf: 'center', padding: 12 },
});

export default ReservationConfirmView;

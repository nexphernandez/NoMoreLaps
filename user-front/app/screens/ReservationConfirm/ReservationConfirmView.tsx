import React from 'react';
import { View, StyleSheet, TouchableOpacity } from 'react-native';
import Typography from '../../components/Typography';
import Card from '../../components/Card';
import ScreenContainer from '../../components/ScreenContainer';
import { useTheme } from '../../context/ThemeContext';
import CustomButton from '../../components/CustomButton';
import Divider from '../../components/Divider';

import { MaterialCommunityIcons } from '@expo/vector-icons';

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
        <Typography variant="h2">Confirm Reservation</Typography>
        <Typography variant="caption" color={theme.textSecondary}>
          Review your stay details before confirming.
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
            <Typography variant="label" color={theme.textSecondary}>SPOT</Typography>
            <Typography variant="h2" color={theme.primary}>P-{summary.spot}</Typography>
          </View>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color={theme.textSecondary}>TOTAL PRICE</Typography>
            <Typography variant="h2" color={theme.success}>{summary.price}</Typography>
          </View>
        </View>

        <Divider marginVertical={16} />

        <View style={styles.row}>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color={theme.textSecondary}>DAY</Typography>
            <Typography variant="h3">{summary.date}</Typography>
          </View>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color={theme.textSecondary}>DURATION</Typography>
            <Typography variant="h3">{summary.duration}</Typography>
          </View>
        </View>

        <Divider marginVertical={16} />

        <View style={styles.row}>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color={theme.textSecondary}>ENTRY</Typography>
            <Typography variant="h3" color={theme.text}>{summary.startTime}</Typography>
          </View>
          <View style={{ flex: 1 }}>
            <Typography variant="label" color={theme.textSecondary}>EXIT (ESTIMATED)</Typography>
            <Typography variant="h3" color={theme.text}>{summary.endTime}</Typography>
          </View>
        </View>
      </Card>

      <View style={styles.disclaimer}>
        {summary.hasSanction && (
          <View style={[styles.warningBox, { backgroundColor: theme.danger + '10', borderColor: theme.danger }]}>
            <View style={{ flexDirection: 'row', alignItems: 'center', marginBottom: 4 }}>
              <MaterialCommunityIcons name="alert" size={18} color={theme.danger} style={{ marginRight: 6 }} />
              <Typography variant="label" color={theme.danger}>DELAY POLICY</Typography>
            </View>
            <Typography variant="caption" style={{ textAlign: 'center' }}>
              A sanction of <Typography variant="caption" style={{fontWeight: 'bold'}}>{summary.sanctionPolicy}</Typography> will be applied for delay.
            </Typography>
          </View>
        )}
        <Typography variant="caption" style={{ textAlign: 'center', marginTop: 16, opacity: 0.6 }}>
          By confirming, you accept the parking's terms of use. Payment will be made directly at the location.
        </Typography>
      </View>

      <View style={styles.footer}>
        <CustomButton title="Confirm & Reserve" onPress={onConfirm} style={{ marginBottom: 12 }} />
        <TouchableOpacity onPress={onCancel} style={styles.cancelBtn}>
          <Typography variant="label" color={theme.danger}>Cancel and go back</Typography>
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

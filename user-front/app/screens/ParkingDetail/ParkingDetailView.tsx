import React from 'react';
import { View, StyleSheet, FlatList, TouchableOpacity, ScrollView } from 'react-native';
import CustomButton from '../../components/CustomButton';
import Badge from '../../components/Badge';
import ScreenContainer from '../../components/ScreenContainer';
import Typography from '../../components/Typography';
import AdBanner from '../../components/AdBanner';
import { useTheme } from '../../context/ThemeContext';

import { ParkingSpot } from '../../services/parkingService';
import { Ad } from '../../services/adService';

const TIME_STEPS = Array.from({ length: 48 }, (_, i) => i * 0.5);

interface ParkingDetailViewProps {
  parkingName: string;
  address?: string;
  spots: ParkingSpot[];
  selectedSpotId: string | null;
  onSelectSpot: (id: string) => void;
  onReserve: () => void;
  selectedDate: Date;
  onDateChange: (date: Date) => void;
  startHour: number;
  onStartHourChange: (h: number) => void;
  endHour: number;
  onEndHourChange: (h: number) => void;
  occupiedReservations: any[];
  ads: Ad[];
}

const ParkingDetailView: React.FC<ParkingDetailViewProps> = ({ 
  parkingName, address, spots, selectedSpotId, onSelectSpot, onReserve,
  selectedDate, onDateChange, startHour, onStartHourChange, endHour, onEndHourChange,
  occupiedReservations, ads
}) => {
  const { theme } = useTheme();

  const isSpotOccupiedInRange = (spotId: number) => {
    const spotReservations = occupiedReservations.filter(r => r.parkingSpotId === spotId);
    if (spotReservations.length === 0) return false;

    for (let t = startHour; t < endHour; t += 0.5) {
      const checkDate = new Date(selectedDate);
      const h = Math.floor(t);
      const m = (t % 1) === 0.5 ? 30 : 0;
      checkDate.setHours(h, m, 0, 0);

      const isHit = spotReservations.some(slot => {
        const start = new Date(slot.startTime);
        const end = new Date(slot.endTime);
        const endWithBuffer = new Date(end.getTime() + 30 * 60000);
        return checkDate >= start && checkDate < endWithBuffer;
      });

      if (isHit) return true;
    }
    return false;
  };

  const getDays = () => {
    const days = [];
    for (let i = 0; i < 7; i++) {
      const d = new Date();
      d.setDate(d.getDate() + i);
      days.push(d);
    }
    return days;
  };

  const isSameDay = (d1: Date, d2: Date) => {
    return d1.getDate() === d2.getDate() && 
           d1.getMonth() === d2.getMonth() && 
           d1.getFullYear() === d2.getFullYear();
  };

  const formatTimeLabel = (t: number) => {
    const h = Math.floor(t);
    const m = (t % 1) === 0.5 ? '30' : '00';
    return `${h < 10 ? `0${h}` : h}:${m}`;
  };
  
  return (
    <ScreenContainer withScroll={false} style={{padding: 0}}>
      <View style={[styles.header, { backgroundColor: theme.background, borderBottomColor: theme.border }]}>
        <Typography variant="h2">{parkingName}</Typography>
        {address && <Typography variant="body" color={theme.textSecondary}>{address}</Typography>}
      </View>

      <View style={{ flex: 1 }}>
        <FlatList
          data={spots}
          numColumns={3}
          keyExtractor={(item) => item.id.toString()}
          contentContainerStyle={styles.list}
          ListHeaderComponent={() => (
            <View style={{ marginBottom: 16 }}>
              {/* DATE SELECTOR */}
              <Typography variant="label" style={styles.sectionTitle}>1. SELECT DAY</Typography>
              <ScrollView horizontal showsHorizontalScrollIndicator={false} contentContainerStyle={styles.scrollContent}>
                {getDays().map((date, idx) => {
                  const isSelected = isSameDay(selectedDate, date);
                  return (
                    <TouchableOpacity 
                      key={idx}
                      onPress={() => onDateChange(date)}
                      style={[
                        styles.dateBtn, 
                        { borderColor: theme.border },
                        isSelected && { backgroundColor: theme.primary, borderColor: theme.primary }
                      ]}
                    >
                      <Typography variant="caption" color={isSelected ? '#FFF' : theme.textSecondary}>
                        {date.toLocaleDateString([], { weekday: 'short' })}
                      </Typography>
                      <Typography variant="h3" color={isSelected ? '#FFF' : theme.text}>
                        {date.getDate()}
                      </Typography>
                    </TouchableOpacity>
                  );
                })}
              </ScrollView>

              {/* ADS FROM ODOO */}
              {ads.length > 0 && (
                <AdBanner ad={ads[0]} style={{ marginTop: 24 }} />
              )}

              {/* TIME SELECTORS */}
              <Typography variant="label" style={styles.sectionTitle}>2. STAY DURATION</Typography>
              <View style={styles.timeRow}>
                <View style={{ flex: 1 }}>
                  <Typography variant="caption" color={theme.textSecondary}>Entry</Typography>
                  <ScrollView horizontal showsHorizontalScrollIndicator={false} contentContainerStyle={styles.miniScroll}>
                    {TIME_STEPS.map(t => {
                      const isToday = isSameDay(selectedDate, new Date());
                      const now = new Date();
                      const currentT = now.getHours() + (now.getMinutes() >= 30 ? 0.5 : 0);
                      const isPast = isToday && t <= currentT;
                      
                      const isSelected = startHour === t;
                      return (
                        <TouchableOpacity 
                          key={t}
                          onPress={() => !isPast && onStartHourChange(t)}
                          disabled={isPast}
                          style={[
                            styles.miniBtn, 
                            {borderColor: theme.border}, 
                            isSelected && {backgroundColor: theme.primary, borderColor: theme.primary},
                            isPast && {backgroundColor: theme.border, opacity: 0.4}
                          ]}
                        >
                          <Typography variant="caption" color={isSelected ? '#FFF' : (isPast ? theme.textSecondary : theme.text)}>
                            {formatTimeLabel(t)}
                          </Typography>
                        </TouchableOpacity>
                      );
                    })}
                  </ScrollView>
                </View>

                <View style={{ flex: 1, marginLeft: 12 }}>
                  <Typography variant="caption" color={theme.textSecondary}>Exit</Typography>
                  <ScrollView horizontal showsHorizontalScrollIndicator={false} contentContainerStyle={styles.miniScroll}>
                    {TIME_STEPS.map(t => {
                      if (t <= startHour) return null;
                      const isSelected = endHour === t;
                      return (
                        <TouchableOpacity 
                          key={t}
                          onPress={() => onEndHourChange(t)}
                          style={[styles.miniBtn, {borderColor: theme.border}, isSelected && {backgroundColor: theme.primary, borderColor: theme.primary}]}
                        >
                          <Typography variant="caption" color={isSelected ? '#FFF' : theme.text}>{formatTimeLabel(t)}</Typography>
                        </TouchableOpacity>
                      );
                    })}
                  </ScrollView>
                </View>
              </View>

              <Typography variant="label" style={styles.sectionTitle}>3. AVAILABLE SPOTS</Typography>
            </View>
          )}
          renderItem={({ item }) => {
            const isFullFromBackend = !item.state;
            const isOccupiedByTime = isSpotOccupiedInRange(item.id);
            const isOccupied = isFullFromBackend || isOccupiedByTime;
            
            const isSelected = selectedSpotId === item.id.toString();

            return (
              <TouchableOpacity 
                style={[
                  styles.spotCard, 
                  { backgroundColor: theme.background, borderColor: theme.border },
                  isOccupied && { backgroundColor: '#FEE2E2', borderColor: theme.danger },
                  isSelected && { backgroundColor: theme.primary, borderColor: theme.primary }
                ]}
                onPress={() => !isOccupied && onSelectSpot(item.id.toString())}
                disabled={isOccupied}
              >
                <Typography variant="h3" color={isOccupied ? theme.danger : isSelected ? '#FFF' : theme.text}>
                  P-{item.number}
                </Typography>
                <View style={{ marginTop: 4 }}>
                  <Badge 
                    label={isOccupied ? 'Full' : 'Free'} 
                    type={isOccupied ? 'danger' : 'success'} 
                  />
                </View>
              </TouchableOpacity>
            );
          }}
          ListEmptyComponent={() => (
            <View style={{ padding: 40, alignItems: 'center' }}>
              <Typography variant="body">No spots available currently.</Typography>
            </View>
          )}
        />
      </View>

      <View style={[styles.footer, { backgroundColor: theme.background, borderTopColor: theme.border }]}>
        <CustomButton
          title={selectedSpotId ? `Reserve Spot P-${spots.find(s => s.id.toString() === selectedSpotId)?.number}` : 'Select a spot'}
          onPress={onReserve}
          disabled={!selectedSpotId}
        />
      </View>
    </ScreenContainer>
  );
};

const styles = StyleSheet.create({
  header: { padding: 24, paddingVertical: 28, borderBottomWidth: 1 },
  list: { padding: 24 },
  sectionTitle: { marginTop: 32, marginBottom: 16, fontWeight: 'bold', fontSize: 13, textTransform: 'uppercase', opacity: 0.8 },
  scrollContent: { paddingRight: 24, paddingBottom: 8 },
  dateBtn: {
    width: 68,
    height: 80,
    borderRadius: 16,
    borderWidth: 1.5,
    justifyContent: 'center',
    alignItems: 'center',
    marginRight: 12,
  },
  timeRow: { flexDirection: 'row', marginTop: 12, gap: 16 },
  miniScroll: { marginTop: 10, paddingBottom: 8 },
  miniBtn: {
    paddingHorizontal: 16,
    paddingVertical: 10,
    borderRadius: 10,
    borderWidth: 1.5,
    marginRight: 10,
    alignItems: 'center',
  },
  spotCard: {
    flex: 1,
    margin: 6,
    height: 85,
    borderRadius: 16,
    justifyContent: 'center',
    alignItems: 'center',
    borderWidth: 2,
    elevation: 2,
    shadowColor: '#000',
    shadowOpacity: 0.05,
    shadowRadius: 5,
    shadowOffset: { width: 0, height: 2 },
  },
  footer: { padding: 24, paddingBottom: 40, borderTopWidth: 1, elevation: 10, shadowColor: '#000', shadowOpacity: 0.1, shadowRadius: 10 },
});

export default ParkingDetailView;

import React from 'react';
import { View, StyleSheet, FlatList, TouchableOpacity } from 'react-native';
import Typography from '../../components/Typography';
import Card from '../../components/Card';
import ScreenContainer from '../../components/ScreenContainer';
import { useTheme } from '../../context/ThemeContext';
import CustomButton from '../../components/CustomButton';
import Badge from '../../components/Badge';

export interface CalendarEvent {
  id: string;
  title: string;
  time: string;
  location: string;
  suggestedParking?: {
    name: string;
    distance: string;
    price: string;
  };
}

interface CalendarSyncViewProps {
  isSynced: boolean;
  onSync: () => void;
  events: CalendarEvent[];
  onReserve: (parkingId: string) => void;
}

const CalendarSyncView: React.FC<CalendarSyncViewProps> = ({ isSynced, onSync, events, onReserve }) => {
  const { theme } = useTheme();

  return (
    <ScreenContainer withScroll={false}>
      {!isSynced ? (
        <View style={styles.emptyContainer}>
          <Typography style={{ fontSize: 60, marginBottom: 20 }}>🗓️</Typography>
          <Typography variant="h2" style={{ textAlign: 'center' }}>Sync your Calendar</Typography>
          <Typography variant="caption" style={{ textAlign: 'center', marginTop: 8, marginBottom: 32 }}>
            Link your system calendar to get smart parking recommendations for your upcoming appointments.
          </Typography>
          <CustomButton title="Connect Calendar" onPress={onSync} />
        </View>
      ) : (
        <FlatList
          data={events}
          keyExtractor={(item) => item.id}
          contentContainerStyle={styles.listContent}
          ListHeaderComponent={() => (
            <View style={styles.header}>
              <Typography variant="h2">Upcoming Events</Typography>
              <Typography variant="caption">Smart parking suggestions for your schedule.</Typography>
            </View>
          )}
          renderItem={({ item }) => (
            <Card style={styles.eventCard}>
              <View style={styles.eventInfo}>
                <View style={[styles.timeBadge, { backgroundColor: theme.lightBackground }]}>
                  <Typography variant="h3" color={theme.primary}>{item.time.split(' ')[0]}</Typography>
                  <Typography variant="label">{item.time.split(' ')[1]}</Typography>
                </View>
                <View style={{ flex: 1, marginLeft: 16 }}>
                  <Typography variant="h3">{item.title}</Typography>
                  <Typography variant="caption">📍 {item.location}</Typography>
                </View>
              </View>

              {item.suggestedParking && (
                <View style={[styles.parkingSuggestion, { backgroundColor: theme.lightBackground, borderColor: theme.border }]}>
                  <View style={{ flex: 1 }}>
                    <Typography variant="label" color={theme.primary} style={{ marginBottom: 4 }}>💡 SUGGESTED PARKING</Typography>
                    <Typography variant="h3">{item.suggestedParking.name}</Typography>
                    <Typography variant="caption">{item.suggestedParking.distance} • {item.suggestedParking.price}</Typography>
                  </View>
                  <TouchableOpacity 
                    style={[styles.bookBtn, { backgroundColor: theme.primary }]}
                    onPress={() => onReserve(item.id)}
                  >
                    <Typography variant="label" color="#FFF">Book</Typography>
                  </TouchableOpacity>
                </View>
              )}
            </Card>
          )}
          ListEmptyComponent={() => (
            <View style={styles.emptyResults}>
              <Typography variant="h3">No upcoming events with locations</Typography>
              <Typography variant="caption">Add locations to your calendar events to see suggestions.</Typography>
            </View>
          )}
        />
      )}
    </ScreenContainer>
  );
};

const styles = StyleSheet.create({
  listContent: { paddingBottom: 20 },
  header: { marginBottom: 24, marginTop: 10 },
  emptyContainer: { 
    flex: 1, 
    alignItems: 'center', 
    justifyContent: 'center', 
    padding: 40 
  },
  eventCard: {
    padding: 16,
    marginBottom: 16,
  },
  eventInfo: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 16,
  },
  timeBadge: {
    width: 60,
    height: 60,
    borderRadius: 12,
    justifyContent: 'center',
    alignItems: 'center',
  },
  parkingSuggestion: {
    padding: 16,
    borderRadius: 12,
    flexDirection: 'row',
    alignItems: 'center',
    borderWidth: 1,
  },
  bookBtn: {
    paddingHorizontal: 16,
    paddingVertical: 8,
    borderRadius: 8,
  },
  emptyResults: {
    alignItems: 'center',
    marginTop: 40,
  }
});

export default CalendarSyncView;

import React from 'react';
import { View, StyleSheet, FlatList, TouchableOpacity } from 'react-native';
import Typography from '../../components/Typography';
import Card from '../../components/Card';
import ScreenContainer from '../../components/ScreenContainer';
import { useTheme } from '../../context/ThemeContext';
import CustomButton from '../../components/CustomButton';
import InputField from '../../components/InputField';

export interface CalendarSuggestion {
  parkingId: number;
  parkingName: string;
  address: string;
  distanceKm: number;
  availableSpots: number;
  suggestedSpotId: number;
  suggestedSpotNumber: number;
  startTime: string;
  endTime: string;
  priceLabel: string;
}

export interface CalendarDeviceEvent {
  id: string;
  title: string;
  location: string;
  startDate: string;
  endDate: string;
}

export interface SyncSummary {
  total: number;
  withLocation: number;
  withoutLocation: number;
}

interface CalendarSyncViewProps {
  syncSummary: SyncSummary | null;
  syncingCalendar: boolean;
  onSyncCalendar: () => void;
  missingLocationTitles: string[];
  dueSoonEvents: string[];
  destination: string;
  startTime: string;
  durationHours: string;
  onDestinationChange: (value: string) => void;
  onStartTimeChange: (value: string) => void;
  onDurationChange: (value: string) => void;
  suggestions: CalendarSuggestion[];
  loading: boolean;
  reservingParkingId: number | null;
  error: string | null;
  onSearch: () => void;
  onReserve: (suggestion: CalendarSuggestion) => void;
  events: CalendarDeviceEvent[];
  onSelectEvent: (event: CalendarDeviceEvent) => void;
}

const CalendarSyncView: React.FC<CalendarSyncViewProps> = ({
  syncSummary,
  syncingCalendar,
  onSyncCalendar,
  missingLocationTitles,
  dueSoonEvents,
  destination,
  startTime,
  durationHours,
  onDestinationChange,
  onStartTimeChange,
  onDurationChange,
  suggestions,
  loading,
  reservingParkingId,
  error,
  onSearch,
  onReserve,
  events,
  onSelectEvent
}) => {
  const { theme } = useTheme();

  return (
    <ScreenContainer withScroll>
      <View style={styles.formCard}>
        <Typography variant="h2" style={styles.title}>Smart Parking Suggestions</Typography>
        <Typography variant="caption" style={styles.subtitle}>
          Sync your calendar and get nearby parking recommendations before your events.
        </Typography>
        <CustomButton title="Sync Google Calendar events" onPress={onSyncCalendar} loading={syncingCalendar} />
        {syncSummary ? (
          <Card style={styles.summaryCard}>
            <Typography variant="h3">Synced events: {syncSummary.total}</Typography>
            <Typography variant="caption">With location: {syncSummary.withLocation}</Typography>
            <Typography variant="caption">Without location: {syncSummary.withoutLocation}</Typography>
          </Card>
        ) : null}
        {missingLocationTitles.length > 0 ? (
          <Card style={styles.summaryCard}>
            <Typography variant="label" color={theme.primary}>Events missing location</Typography>
            <Typography variant="caption">
              Add a destination in Google Calendar to get automatic recommendations.
            </Typography>
            <Typography variant="caption">{missingLocationTitles.slice(0, 3).join(' · ')}</Typography>
          </Card>
        ) : null}
        {events.length > 0 ? (
          <View style={styles.eventsContainer}>
            <Typography variant="label" style={{ marginBottom: 8 }}>Found Events (Select to fill)</Typography>
            <FlatList
              horizontal
              showsHorizontalScrollIndicator={false}
              data={events}
              keyExtractor={(item) => item.id}
              renderItem={({ item }) => (
                <TouchableOpacity
                  style={[styles.eventChip, { borderColor: theme.border, backgroundColor: theme.background }]}
                  onPress={() => onSelectEvent(item)}
                >
                  <Typography variant="label">{item.title}</Typography>
                  {item.location ? <Typography variant="caption" color={theme.textSecondary}>📍 {item.location.slice(0, 15)}...</Typography> : null}
                </TouchableOpacity>
              )}
            />
          </View>
        ) : null}
        <InputField
          label="Destination"
          value={destination}
          onChangeText={onDestinationChange}
          placeholder="e.g. Paseo de la Castellana, 100"
        />
        <InputField
          label="Start time (YYYY-MM-DDTHH:mm:ss)"
          value={startTime}
          onChangeText={onStartTimeChange}
          placeholder="2026-04-29T14:30:00"
        />
        <InputField
          label="Duration (hours)"
          value={durationHours}
          onChangeText={onDurationChange}
          placeholder="1"
          keyboardType="numeric"
        />
        <CustomButton title="Search Suggestions" onPress={onSearch} loading={loading} />
        {error ? <Typography variant="error" style={styles.errorText}>{error}</Typography> : null}
      </View>

      <FlatList
        data={suggestions}
        keyExtractor={(item) => item.parkingId.toString()}
        contentContainerStyle={styles.listContent}
        scrollEnabled={false}
        ListHeaderComponent={() => (
          <View style={styles.header}>
            <Typography variant="h3">Suggested Parkings</Typography>
            <Typography variant="caption">Closest options with available spots right now.</Typography>
          </View>
        )}
        renderItem={({ item }) => (
          <Card style={styles.eventCard}>
            <View style={styles.eventInfo}>
              <View style={[styles.timeBadge, { backgroundColor: theme.lightBackground }]}>
                <Typography variant="h3" color={theme.primary}>{item.availableSpots}</Typography>
                <Typography variant="label">spots</Typography>
              </View>
              <View style={{ flex: 1, marginLeft: 16 }}>
                <Typography variant="h3">{item.parkingName}</Typography>
                <Typography variant="caption">📍 {item.address}</Typography>
                <Typography variant="caption">{item.distanceKm.toFixed(2)} km • {item.priceLabel}</Typography>
              </View>
            </View>

            <View style={[styles.parkingSuggestion, { backgroundColor: theme.lightBackground, borderColor: theme.border }]}>
              <View style={{ flex: 1 }}>
                <Typography variant="label" color={theme.primary} style={{ marginBottom: 4 }}>RESERVATION WINDOW</Typography>
                <Typography variant="caption">{item.startTime.replace('T', ' ')}</Typography>
                <Typography variant="caption">{item.endTime.replace('T', ' ')}</Typography>
              </View>
              <TouchableOpacity
                style={[styles.bookBtn, { backgroundColor: theme.primary }]}
                onPress={() => onReserve(item)}
                disabled={reservingParkingId === item.parkingId}
              >
                <Typography variant="label" color="#FFF">
                  {reservingParkingId === item.parkingId ? 'Booking...' : 'Book'}
                </Typography>
              </TouchableOpacity>
            </View>
          </Card>
        )}
        ListEmptyComponent={() => (
          <View style={styles.emptyResults}>
            <Typography variant="h3">No suggestions yet</Typography>
            <Typography variant="caption">
              Search by destination to see nearby parking recommendations.
            </Typography>
          </View>
        )}
      />
    </ScreenContainer>
  );
};

const styles = StyleSheet.create({
  listContent: { paddingBottom: 20 },
  formCard: {
    marginTop: 8,
    marginBottom: 16,
    gap: 8
  },
  title: {
    marginBottom: 4
  },
  summaryCard: {
    padding: 12,
    marginBottom: 8
  },
  subtitle: {
    marginBottom: 8
  },
  header: { marginBottom: 24, marginTop: 10 },
  errorText: {
    marginTop: 8
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
  },
  eventsContainer: {
    marginVertical: 8,
    height: 80
  },
  eventChip: {
    padding: 10,
    borderRadius: 12,
    borderWidth: 1,
    marginRight: 8,
    height: 60,
    justifyContent: 'center',
    minWidth: 120
  }
});

export default CalendarSyncView;

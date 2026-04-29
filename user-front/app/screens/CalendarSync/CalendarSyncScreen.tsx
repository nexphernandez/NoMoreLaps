import React, { useState } from 'react';
import { Alert } from 'react-native';
import CalendarSyncView, { CalendarSuggestion, CalendarDeviceEvent } from './CalendarSyncView';
import calendarService from '../../services/calendarService';
import { useAuth } from '../../context/AuthContext';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { RootStackParamList } from '../../navigation/RootNavigator';

const CalendarSyncScreen = () => {
  const today = new Date();
  const [destination, setDestination] = useState('');
  const [startTime, setStartTime] = useState(today.toISOString().slice(0, 19));
  const [durationHours, setDurationHours] = useState('1');
  const [suggestions, setSuggestions] = useState<CalendarSuggestion[]>([]);
  const [loading, setLoading] = useState(false);
  const [syncingCalendar, setSyncingCalendar] = useState(false);
  const [syncSummary, setSyncSummary] = useState<{ total: number; withLocation: number; withoutLocation: number } | null>(null);
  const [missingLocationTitles, setMissingLocationTitles] = useState<string[]>([]);
  const [dueSoonEvents, setDueSoonEvents] = useState<string[]>([]);
  const [reservingParkingId, setReservingParkingId] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [allEvents, setAllEvents] = useState<CalendarDeviceEvent[]>([]);
  const { user } = useAuth();
  const navigation = useNavigation<StackNavigationProp<RootStackParamList>>();

  const validateInput = (): boolean => {
    if (!destination.trim()) {
      setError('Please provide a destination before searching.');
      return false;
    }
    if (!/^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}$/.test(startTime)) {
      setError('Start time must follow YYYY-MM-DDTHH:mm:ss format.');
      return false;
    }
    if (Number.isNaN(Number(durationHours)) || Number(durationHours) <= 0) {
      setError('Duration must be a number greater than zero.');
      return false;
    }
    return true;
  };

  const handleSearch = async () => {
    if (!validateInput()) return;
    try {
      setLoading(true);
      setError(null);
      const data = await calendarService.searchSuggestions({
        destination,
        startTime,
        durationHours: Number(durationHours),
      });
      setSuggestions(data);
      if (data.length === 0) {
        setError('No nearby parking with available spots was found for this destination.');
      }
    } catch (searchError: any) {
      setError(searchError.message || 'Failed to search suggestions.');
    } finally {
      setLoading(false);
    }
  };

  const handleSyncCalendar = async () => {
    try {
      setSyncingCalendar(true);
      setError(null);
      const synced = await calendarService.syncCalendarEvents();
      setSyncSummary({
        total: synced.total,
        withLocation: synced.withLocation.length,
        withoutLocation: synced.withoutLocation.length
      });
      setMissingLocationTitles(synced.withoutLocation.map((event) => event.title));

      const dueSoon = await calendarService.getDueSoonSuggestions(synced.withLocation, 30);
      if (dueSoon.length > 0) {
        const top = dueSoon[0];
        setDestination(top.event.location);
        setStartTime(new Date(top.event.startDate).toISOString().slice(0, 19));
        setDurationHours('1');
        setSuggestions(top.suggestions);
      }
      setDueSoonEvents(dueSoon.map((entry) => entry.event.title));
      setAllEvents([...synced.withLocation, ...synced.withoutLocation]);
      
      if (synced.withoutLocation.length > 0) {
        setError('Some events do not have location. Add location in Google Calendar to get automatic recommendations.');
      }
    } catch (syncError: any) {
      setError(syncError.message || 'Failed to sync calendar events.');
    } finally {
      setSyncingCalendar(false);
    }
  };

  const handleSelectEvent = (event: CalendarDeviceEvent) => {
    const start = new Date(event.startDate);
    const end = new Date(event.endDate);
    
    // Round start time to nearest half hour for consistency
    const minutes = start.getMinutes();
    const roundedStart = new Date(start);
    if (minutes >= 45) {
      roundedStart.setHours(start.getHours() + 1, 0, 0, 0);
    } else if (minutes >= 15) {
      roundedStart.setHours(start.getHours(), 30, 0, 0);
    } else {
      roundedStart.setHours(start.getHours(), 0, 0, 0);
    }

    setDestination(event.location);
    setStartTime(roundedStart.toISOString().slice(0, 19));
    
    const duration = Math.max(1, Math.round((end.getTime() - start.getTime()) / 3600000));
    setDurationHours(duration.toString());
  };

  const handleReserve = (suggestion: CalendarSuggestion) => {
    if (!user) {
      setError('You need to be logged in to create a reservation.');
      return;
    }

    const start = new Date(suggestion.startTime);
    const end = new Date(suggestion.endTime);
    
    const roundToHalf = (date: Date) => {
      const hours = date.getHours();
      const minutes = date.getMinutes();
      return hours + (minutes >= 45 ? 1 : minutes >= 15 ? 0.5 : 0);
    };

    navigation.navigate('ParkingDetail', {
      parkingId: suggestion.parkingId.toString(),
      initialDate: start.toISOString(),
      initialStartHour: roundToHalf(start),
      initialEndHour: roundToHalf(end)
    });
  };

  return (
    <CalendarSyncView
      syncSummary={syncSummary}
      syncingCalendar={syncingCalendar}
      onSyncCalendar={handleSyncCalendar}
      missingLocationTitles={missingLocationTitles}
      dueSoonEvents={dueSoonEvents}
      destination={destination}
      startTime={startTime}
      durationHours={durationHours}
      onDestinationChange={setDestination}
      onStartTimeChange={setStartTime}
      onDurationChange={setDurationHours}
      suggestions={suggestions}
      loading={loading}
      reservingParkingId={reservingParkingId}
      error={error}
      onSearch={handleSearch}
      onReserve={handleReserve}
      events={allEvents}
      onSelectEvent={handleSelectEvent}
    />
  );
};

export default CalendarSyncScreen;

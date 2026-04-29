import api from './api';
import { Reservation } from './reservationService';

export interface CalendarDeviceEvent {
  id: string;
  title: string;
  location: string;
  startDate: string;
  endDate: string;
}

export interface SyncedCalendarEvents {
  total: number;
  withLocation: CalendarDeviceEvent[];
  withoutLocation: CalendarDeviceEvent[];
}

export interface SmartScheduleInput {
  destination: string;
  startTime: string;
  durationHours: number;
  radiusKm?: number;
}

export interface ParkingSuggestion {
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

interface GeocodeResponse {
  formattedAddress: string;
  latitude: number;
  longitude: number;
}

interface RecommendationResponse {
  suggestions: ParkingSuggestion[];
}

const toLocalIso = (date: Date): string => {
  const offset = date.getTimezoneOffset();
  const local = new Date(date.getTime() - offset * 60000);
  return local.toISOString().slice(0, 19);
};

const readDeviceEvents = async (): Promise<CalendarDeviceEvent[]> => {
  try {
    const ExpoCalendar = require('expo-calendar');
    const permission = await ExpoCalendar.requestCalendarPermissionsAsync();
    if (permission.status !== 'granted') {
      throw new Error('Calendar permission denied.');
    }

    const calendars = await ExpoCalendar.getCalendarsAsync(ExpoCalendar.EntityTypes.EVENT);
    const now = new Date();
    const monthAhead = new Date(now);
    monthAhead.setDate(monthAhead.getDate() + 30);

    const eventsByCalendar = await Promise.all(
      calendars.map((calendar: any) => ExpoCalendar.getEventsAsync([calendar.id], now, monthAhead))
    );

    return eventsByCalendar
      .flat()
      .map((event: any) => ({
        id: String(event.id),
        title: event.title || 'Untitled event',
        location: event.location || '',
        startDate: event.startDate,
        endDate: event.endDate,
      }))
      .sort((a, b) => new Date(a.startDate).getTime() - new Date(b.startDate).getTime());
  } catch (error: any) {
    throw new Error(error.message || 'Could not read calendar events.');
  }
};

const syncCalendarEvents = async (): Promise<SyncedCalendarEvents> => {
  const events = await readDeviceEvents();
  return {
    total: events.length,
    withLocation: events.filter((event) => !!event.location.trim()),
    withoutLocation: events.filter((event) => !event.location.trim()),
  };
};

const geocodeDestination = async (query: string): Promise<GeocodeResponse> => {
  const response = await api.post<GeocodeResponse>('smart-calendar/geocode', { query });
  return response.data;
};

const searchSuggestions = async (input: SmartScheduleInput): Promise<ParkingSuggestion[]> => {
  const response = await api.post<RecommendationResponse>('smart-calendar/recommendations', {
    destinationText: input.destination.trim(),
    startTime: input.startTime,
    durationHours: input.durationHours,
    radiusKm: input.radiusKm ?? 2,
  });
  return response.data.suggestions || [];
};

const getDueSoonSuggestions = async (
  events: CalendarDeviceEvent[],
  leadMinutes: number = 30
): Promise<{ event: CalendarDeviceEvent; suggestions: ParkingSuggestion[] }[]> => {
  const now = new Date();
  const dueThreshold = new Date(now.getTime() + leadMinutes * 60 * 1000);
  const dueSoon = events.filter((event) => {
    const eventStart = new Date(event.startDate);
    return eventStart >= now && eventStart <= dueThreshold && !!event.location.trim();
  });

  const suggestionsPerEvent = await Promise.all(
    dueSoon.map(async (event) => {
      const eventStart = new Date(event.startDate);
      const eventEnd = new Date(event.endDate);
      const durationHours = Math.max(1, Math.round((eventEnd.getTime() - eventStart.getTime()) / 3600000));
      const suggestions = await searchSuggestions({
        destination: event.location,
        startTime: toLocalIso(eventStart),
        durationHours,
        radiusKm: 2,
      });
      return { event, suggestions };
    })
  );

  return suggestionsPerEvent.filter((item) => item.suggestions.length > 0);
};

const buildReservationFromSuggestion = (suggestion: ParkingSuggestion, userId: number): Reservation => ({
  parkingSpotId: suggestion.suggestedSpotId,
  userId,
  startTime: suggestion.startTime,
  endTime: suggestion.endTime,
  price: 0,
  state: 'ACTIVE',
});

export default {
  syncCalendarEvents,
  geocodeDestination,
  searchSuggestions,
  getDueSoonSuggestions,
  buildReservationFromSuggestion,
};

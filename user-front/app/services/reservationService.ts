import api from './api';
import databaseService from './databaseService';

/**
 * Interface representing a Reservation.
 */
export interface Reservation {
  id?: number;
  startTime: string;
  endTime: string;
  price?: number;
  state?: string; 
  creationTime?: string;
  parkingSpotId?: number;
  parkingId?: number;
  userId?: number;
  parkingName?: string;
}

const reservationService = {
  /**
   * Creates a new reservation for the current user.
   * If offline, saves to local queue for later sync.
   */
  create: async (reservation: Reservation): Promise<Reservation> => {
    try {
      const response = await api.post<Reservation>('reservations', reservation);
      return response.data;
    } catch (error: any) {
      console.log('Reservation Error Object:', JSON.stringify(error));
      
      // If it's a network error (no response)
      if (!error.response || error.code === 'ERR_NETWORK') {
        console.warn('OFFLINE DETECTED: saving reservation to local queue...');
        try {
          await databaseService.addPendingReservation({
            spotId: reservation.parkingSpotId,
            parkingId: reservation.parkingId || 0,
            startTime: reservation.startTime,
            endTime: reservation.endTime
          });
          console.log('SUCCESS: Reservation saved in SQLite');
          
          return {
            ...reservation,
            id: -1, 
            state: 'PENDING_SYNC'
          } as Reservation;
        } catch (dbError) {
          console.error('DB ERROR while saving offline:', dbError);
        }
      }
      throw new Error(error.response?.data?.message || 'Failed to create reservation');
    }
  },

  /**
   * Fetches all reservations for a specific user.
   */
  getByUserId: async (userId: number): Promise<Reservation[]> => {
    try {
      const response = await api.get<Reservation[]>(`reservations/user/${userId}`);
      databaseService.saveUserReservations(response.data).catch(err => console.error('History cache error:', err));
      return response.data;
    } catch (error: any) {
      console.warn('[OFFLINE] Fetching reservation history from local cache...');
      const localData = await databaseService.getUserReservations();
      if (localData.length > 0) {
        console.log(`[OFFLINE] Loaded ${localData.length} reservations from cache.`);
        return localData.map((row: any) => JSON.parse(row.data));
      }
      console.error('[OFFLINE] No reservations found in local cache.');
      throw new Error('No offline reservations available');
    }
  },

  /**
   * Fetches a single reservation details by its ID.
   */
  getById: async (id: number): Promise<Reservation> => {
    try {
      const response = await api.get<Reservation>(`reservations/${id}`);
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Reservation not found');
    }
  },

  /**
   * Cancels an existing reservation.
   */
  cancel: async (id: number): Promise<void> => {
    try {
      await api.delete(`reservations/${id}`);
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Failed to cancel reservation');
    }
  },

  /**
   * Fetches all active reservations for all spots in a parking.
   */
  getOccupiedByParking: async (parkingId: number): Promise<Reservation[]> => {
    try {
      const response = await api.get<Reservation[]>(`reservations/parking/${parkingId}/occupied`);
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Failed to fetch parking reservations');
    }
  }
};

export default reservationService;

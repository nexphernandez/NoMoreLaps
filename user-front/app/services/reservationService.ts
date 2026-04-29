import api from './api';

/**
 * Interface representing a Reservation.
 */
export interface Reservation {
  id?: number;
  startTime: string;
  endTime: string;
  price: number;
  state: string; 
  creationTime?: string;
  parkingSpotId?: number;
  userId?: number;
  parkingName?: string;
}

const reservationService = {
  /**
   * Creates a new reservation for the current user.
   */
  create: async (reservation: Reservation): Promise<Reservation> => {
    try {
      const response = await api.post<Reservation>('reservations', reservation);
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Failed to create reservation');
    }
  },

  /**
   * Fetches all reservations for a specific user.
   */
  getByUserId: async (userId: number): Promise<Reservation[]> => {
    try {
      const response = await api.get<Reservation[]>(`reservations/user/${userId}`);
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Failed to fetch user reservations');
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

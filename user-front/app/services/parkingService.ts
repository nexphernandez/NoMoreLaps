import api from './api';

/**
 * Interface representing a Parking facility.
 */
export interface Parking {
  id: number;
  name: string;
  address: string;
  latitude: number;
  longitude: number;
  openingTime: string;
  closingTime: string;
}

/**
 * Interface representing a specific Parking Spot.
 */
export interface ParkingSpot {
  id: number;
  state: boolean;
  number: number;
  registerDate: string;
}

const parkingService = {
  /**
   * Fetches all registered parking facilities.
   */
  getAll: async (): Promise<Parking[]> => {
    try {
      const response = await api.get<Parking[]>('/parkings');
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Failed to fetch parkings');
    }
  },

  /**
   * Fetches a single parking facility by its ID.
   */
  getById: async (id: number): Promise<Parking> => {
    try {
      const response = await api.get<Parking>(`/parkings/${id}`);
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Parking not found');
    }
  },

  /**
   * Searches for parkings near a specific location.
   * @param lat Latitude of the search center.
   * @param lng Longitude of the search center.
   * @param radius Radius in kilometers.
   */
  getNearby: async (lat: number, lng: number, radius: number = 5): Promise<Parking[]> => {
    try {
      const response = await api.get<Parking[]>(`/parkings/nearby`, {
        params: { lat, lng, radius }
      });
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Error searching nearby parkings');
    }
  },

  /**
   * Searches for parkings by name or address query.
   */
  search: async (query: string): Promise<Parking[]> => {
    try {
      const response = await api.get<Parking[]>(`/parkings/search`, {
        params: { query }
      });
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Search failed');
    }
  },

  /**
   * Fetches all parking spots for a specific parking facility.
   */
  getAvailableSpots: async (parkingId: number): Promise<ParkingSpot[]> => {
    try {
      const response = await api.get<ParkingSpot[]>(`/parking-spots/parking/${parkingId}/available`);
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Failed to fetch available spots');
    }
  }
};

export default parkingService;

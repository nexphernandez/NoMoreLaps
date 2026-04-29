import api from './api';
import databaseService from './databaseService';

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
  sanctionAmount?: number;
  sanctionIntervalInMinutes?: number;
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
   * Caches data locally for offline use.
   */
  getAll: async (): Promise<Parking[]> => {
    try {
      const response = await api.get<Parking[]>('parkings');
      databaseService.saveParkings(response.data).catch(err => console.error('Cache error:', err));
      return response.data;
    } catch (error: any) {
      console.warn('Network failed, trying local cache...');
      const localData = await databaseService.getParkings();
      if (localData.length > 0) {
        return localData.map((row: any) => JSON.parse(row.data));
      }
      throw new Error(error.response?.data?.message || 'Failed to fetch parkings and no local cache found');
    }
  },

  /**
   * Fetches a single parking facility by its ID.
   */
  getById: async (id: number): Promise<Parking> => {
    try {
      const response = await api.get<Parking>(`parkings/${id}`);
      // Also update this single parking in cache
      databaseService.saveParkings([response.data]).catch(err => console.error('Single parking cache error:', err));
      return response.data;
    } catch (error: any) {
      console.warn(`[OFFLINE] Fetching parking ${id} from local cache...`);
      const localData = await databaseService.getParkings();
      // Robust comparison converting both to Number
      const parking = localData.find((row: any) => Number(row.id) === Number(id));
      if (parking) {
        console.log(`[OFFLINE] Found parking ${id} in cache.`);
        return JSON.parse((parking as any).data);
      }
      console.error(`[OFFLINE] Parking ${id} NOT FOUND in cache. Available IDs:`, localData.map((r:any) => r.id));
      throw new Error('Parking not found in local cache');
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
      const response = await api.get<Parking[]>(`parkings/nearby`, {
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
      const response = await api.get<Parking[]>(`parkings/search`, {
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
      const response = await api.get<ParkingSpot[]>(`parking-spots/parking/${parkingId}/available`);
      databaseService.saveParkingSpots(parkingId, response.data).catch(err => console.error('Spot cache error:', err));
      return response.data;
    } catch (error: any) {
      console.warn('Network failed, trying local spot cache...');
      const localData = await databaseService.getParkingSpots(parkingId);
      if (localData.length > 0) {
        // Filter those that were available (state 1) in our cache
        return localData
          .filter((row: any) => row.state === 1)
          .map((row: any) => JSON.parse(row.data));
      }
      throw new Error(error.response?.data?.message || 'Failed to fetch available spots');
    }
  },

  /**
   * Fetches all parking spots for a specific parking facility (including occupied).
   */
  getAllSpots: async (parkingId: number): Promise<ParkingSpot[]> => {
    try {
      const response = await api.get<ParkingSpot[]>(`parking-spots/parking/${parkingId}`);
      databaseService.saveParkingSpots(parkingId, response.data).catch(err => console.error('Spot cache error:', err));
      return response.data;
    } catch (error: any) {
      console.warn(`[OFFLINE] Fetching spots for parking ${parkingId} from local cache...`);
      const localData = await databaseService.getParkingSpots(parkingId);
      if (localData.length > 0) {
        console.log(`[OFFLINE] Loaded ${localData.length} spots from cache.`);
        return localData.map((row: any) => JSON.parse(row.data));
      }
      console.error(`[OFFLINE] No spots found for parking ${parkingId} in cache.`);
      throw new Error('No offline spots available');
    }
  }
};

export default parkingService;

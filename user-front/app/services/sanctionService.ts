import api from './api';
import databaseService from './databaseService';

/**
 * Interface representing a Sanction (fine).
 */
export interface Sanction {
  id: number;
  amount: number;
  reason: string;   // backend field name
  paid: boolean;    // backend field name (not isPaid)
  arrivalTime: string; // backend field name (not sanctionDate)
}

const sanctionService = {
  /**
   * Fetches all sanctions for a specific user.
   */
  getByUserId: async (userId: number): Promise<Sanction[]> => {
    try {
      const response = await api.get<Sanction[]>(`sanctions/user/${userId}`);
      databaseService.saveUserSanctions(response.data).catch(err => console.error('Sanction cache error:', err));
      return response.data;
    } catch (error: any) {
      console.warn('Network failed, trying local sanction cache...');
      const localData = await databaseService.getUserSanctions();
      if (localData.length > 0) {
        return localData.map((row: any) => JSON.parse(row.data));
      }
      throw new Error(error.response?.data?.message || 'Failed to fetch sanctions');
    }
  }
};

export default sanctionService;

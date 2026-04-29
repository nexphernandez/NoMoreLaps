import api from './api';

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
      const response = await api.get<Sanction[]>(`/sanctions/user/${userId}`);
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Failed to fetch sanctions');
    }
  },

  /**
   * Marks a sanction as paid.
   */
  pay: async (id: number): Promise<Sanction> => {
    try {
      const response = await api.patch<Sanction>(`/sanctions/${id}/pay`);
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Payment failed');
    }
  }
};

export default sanctionService;

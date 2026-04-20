import api from './api';
import { UserData } from '../context/AuthContext';

export interface UserResponse {
  id: number;
  name: string;
  email: string;
  calendarEnable: boolean;
}

const userService = {
  /**
   * Fetches user details by email.
   * This is used after login to populate the AuthContext.
   */
  getUserByEmail: async (email: string): Promise<UserData> => {
    try {
      const response = await api.get<UserResponse>(`/users/email/${email}`);
      const data = response.data;
      
      // Map backend response to our AuthContext UserData interface
      return {
        name: data.name,
        email: data.email,
        phone: '+34 600 000 000', // Backend doesn't seem to have phone yet, using placeholder
      };
    } catch (error: any) {
      const errorMsg = error.response?.data?.message || 'Error fetching user profile';
      throw new Error(errorMsg);
    }
  }
};

export default userService;

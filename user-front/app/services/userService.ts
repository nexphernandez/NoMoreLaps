import api from './api';

/**
 * Interface representing a User.
 */
export interface User {
  id: number;
  name: string;
  email: string;
  calendarEnable: boolean;
  createAt?: string;
}

const userService = {
  /**
   * Fetches the profile of a user by ID.
   */
  getProfile: async (id: number): Promise<User> => {
    try {
      const response = await api.get<User>(`users/${id}`);
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Failed to fetch profile');
    }
  },

  /**
   * Fetches the profile of a user by Email.
   */
  getUserByEmail: async (email: string): Promise<User> => {
    try {
      const response = await api.get<User>(`users/email/${email}`);
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'User not found');
    }
  },

  /**
   * Updates user profile information.
   */
  updateProfile: async (user: User): Promise<User> => {
    try {
      const response = await api.put<User>(`users/${user.id}`, user);
      return response.data;
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Failed to update profile');
    }
  },

  /**
   * Deletes the user account.
   */
  deleteAccount: async (id: number): Promise<void> => {
    try {
      await api.delete(`users/${id}`);
    } catch (error: any) {
      throw new Error(error.response?.data?.message || 'Failed to delete account');
    }
  }
};

export default userService;

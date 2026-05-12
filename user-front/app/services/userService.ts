import api from './api';
import databaseService from './databaseService';

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
      databaseService.saveUserProfile(id, response.data).catch(err => console.error('Profile cache error:', err));
      return response.data;
    } catch (error: any) {
      console.warn('Network failed, trying local profile cache...');
      const localData = await databaseService.getUserProfile(id) as { data: string } | null;
      if (localData) {
        return JSON.parse(localData.data);
      }
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
      databaseService.saveUserProfile(user.id, response.data).catch(err => console.error('Profile cache update error:', err));
      return response.data;
    } catch (error: any) {
      if (!error.response) {
        console.warn('Network error, queueing profile update...');
        await databaseService.addPendingUpdate('PROFILE', user);
        databaseService.saveUserProfile(user.id, user).catch(err => console.error('Optimistic cache error:', err));
        return user;
      }
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

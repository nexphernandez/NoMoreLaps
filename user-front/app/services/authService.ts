import api from './api';
/**
 * Interfaces to define the structure .
 */
export interface LoginData {
  email: string;
  password: string;
}
export interface RegisterData {
  name: string;
  email: string;
  password: string;
}
export interface AuthResponse {
  token: string;
  message: string;
}
const authService = {
  /**
   * Sends user credentials to the backend for authentication.
   */
  login: async (data: LoginData): Promise<AuthResponse> => {
    try {
      const response = await api.post<AuthResponse>('auth/login', data);
      return response.data;
    } catch (error: any) {
      throw error;
    }
  },
  /**
   * Registers a new user in the system.
   */
  register: async (data: RegisterData): Promise<any> => {
    try {
      const response = await api.post('auth/register', data);
      return response.data;
    } catch (error: any) {
      throw error;
    }
  }
};
export default authService;
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
      // Use our 'api' instance to make a POST request to '/auth/login'
      const response = await api.post<AuthResponse>('auth/login', data);
      return response.data;
    } catch (error: any) {
      const errorMsg = error.response?.data?.message || 'Error occurred during login';
      throw new Error(errorMsg);
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
      const errorMsg = error.response?.data?.message || 'Error occurred during registration';
      throw new Error(errorMsg);
    }
  }
};
export default authService;
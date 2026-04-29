import axios from 'axios';
import * as SecureStore from 'expo-secure-store';

/**
 * Base Axios configuration for connecting to the Spring Boot backend.
 * 
 * NOTE: 
 * - For Android Emulator: use http://10.0.2.2:8080/api
 * - For Physical Device: use your machine's local IP (e.g. http://192.168.1.XX:8080/api)
 */
const API_URL = 'http://192.168.1.13:8080/api'; 

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * Request interceptor to automatically inject the JWT token 
 * from SecureStore into any outgoing request.
 */
api.interceptors.request.use(
  async (config) => {
    const token = await SecureStore.getItemAsync('userToken');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;

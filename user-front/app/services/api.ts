import axios from 'axios';
import * as SecureStore from 'expo-secure-store';
import { Alert } from 'react-native';

/**
 * Base Axios configuration for connecting to the Spring Boot backend.
 */
const API_URL = 'https://compiled-originally-base-hired.trycloudflare.com/api';

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * Request interceptor to automatically inject the JWT token.
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

/**
 * Response interceptor to handle token expiration (401).
 */
api.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response && error.response.status === 401) {
      const { url, method } = error.config;

      const isAuthRoute = url.includes('auth/');
      const isPublicGet = method === 'get' && (url.includes('parkings') || url.includes('parking-spots'));

      if (!isAuthRoute && !isPublicGet) {
        console.warn('Token expired on protected route. Clearing credentials.');
        await SecureStore.deleteItemAsync('userToken');
        await SecureStore.deleteItemAsync('userData');

        Alert.alert(
          'Session Expired',
          'Your session has expired. Please log in again to perform this action.',
          [{ text: 'OK' }]
        );
      } else if (isPublicGet) {
        await SecureStore.deleteItemAsync('userToken');
        await SecureStore.deleteItemAsync('userData');
      }
    }
    return Promise.reject(error);
  }
);

export default api;

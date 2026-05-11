import Constants from 'expo-constants';

const API_URL = Constants.expoConfig?.extra?.apiUrl || 'http://10.0.2.2:8080/api';

export interface Ad {
  name: string;
  adType: 'banner' | 'featured' | 'push';
  imageUrl: string;
  targetUrl: string;
}

export const adService = {
  getActiveAds: async (): Promise<Ad[]> => {
    try {
      const response = await fetch(`${API_URL}/ads/active`);
      if (!response.ok) throw new Error('Error fetching ads');
      return await response.json();
    } catch (error) {
      console.error('AdService Error:', error);
      return [];
    }
  }
};

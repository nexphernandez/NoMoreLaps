import api from './api';

export interface Ad {
  name: string;
  image_url: string;
  target_url: string;
  description: string;
}

export const adService = {
  getActiveAds: async (): Promise<Ad[]> => {
    try {
      const response = await api.get<Ad[]>('ads/active');
      return response.data;
    } catch (error) {
      console.error('AdService Error:', error);
      return [];
    }
  }
};

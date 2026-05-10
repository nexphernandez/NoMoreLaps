export interface Parking {
  id: number;
  name: string;
  address: string;
  latitude: number;
  longitude: number;
  companyId: number;
  sanctionAmount: number;
  sanctionIntervalInMinutes: number;
  status: 'Open' | 'Closed';
  totalSpots?: number;
}

export interface ParkingRequest {
  name: string;
  address: string;
  latitude: number;
  longitude: number;
  companyId: number;
  sanctionAmount: number;
  sanctionIntervalInMinutes: number;
  totalSpots: number;
}

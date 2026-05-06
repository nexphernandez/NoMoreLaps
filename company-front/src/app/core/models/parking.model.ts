export interface Parking {
  id: string;
  name: string;
  address: string;
  latitude: number;
  longitude: number;
  companyId: string;
  sanctionAmount: number;
  sanctionIntervalInMinutes: number;
  status: 'Abierto' | 'Cerrado';
  totalSpots?: number;
}

export interface ParkingRequest {
  name: string;
  address: string;
  latitude: number;
  longitude: number;
  companyId: string;
  sanctionAmount: number;
  sanctionIntervalInMinutes: number;
  totalSpots: number;
}

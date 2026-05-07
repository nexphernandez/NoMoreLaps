export interface Parking {
  id: number;
  name: string;
  address: string;
  latitude: number;
  longitude: number;
  companyId: number;
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
  companyId: number;
  sanctionAmount: number;
  sanctionIntervalInMinutes: number;
  totalSpots: number;
}

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
}

export interface ParkingRequest {
  name: string;
  address: string;
  latitude: number;
  longitude: number;
  companyId: string;
  sanctionAmount: number;
  sanctionIntervalInMinutes: number;
}

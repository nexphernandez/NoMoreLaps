export interface Reservation {
  id: number;
  startTime: string;
  endTime: string;
  price: number;
  state: string;
  creationTime: string;
  parkingSpotId: number;
  userId: number;
  parkingName: string;
  userName: string;
  basePrice: number;
  sanctionPrice: number;
  paid: boolean;
}

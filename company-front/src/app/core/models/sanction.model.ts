export interface Sanction {
  id: number;
  amount: number;
  reason: string;
  paid: boolean;
  arrivalTime: string;
  userId: number;
  userName: string;
  parkingName: string;
}

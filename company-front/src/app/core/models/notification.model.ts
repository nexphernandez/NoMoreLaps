export interface Notification {
  id: number;
  message: string;
  type: 'RESERVATION' | 'SANCTION' | 'CANCEL';
  isRead: boolean;
  companyId: number;
  createdAt: string;
}

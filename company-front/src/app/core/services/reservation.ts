import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reservation } from '../models/reservation.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private apiUrl = `${environment.apiUrl}/reservations`;

  constructor(private http: HttpClient) {}

  getReservationsByCompany(companyId: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.apiUrl}/company/${companyId}`);
  }

  getReservationsByParking(parkingId: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.apiUrl}/parking/${parkingId}/occupied`);
  }

  updateReservation(id: number, reservation: Partial<Reservation>): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.apiUrl}/${id}`, reservation);
  }

  deleteReservation(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  updatePaymentStatus(id: number, paid: boolean): Observable<Reservation> {
    return this.http.patch<Reservation>(`${this.apiUrl}/${id}/payment-status?paid=${paid}`, {});
  }
}

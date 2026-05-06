import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Parking, ParkingRequest } from '../models/parking.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ParkingService {
  private readonly apiUrl = `${environment.apiUrl}/parkings`;

  constructor(private http: HttpClient) {}

  getParkingsByCompany(companyId: number): Observable<Parking[]> {
    return this.http.get<Parking[]>(`${this.apiUrl}/company/${companyId}`);
  }

  createParking(parking: ParkingRequest): Observable<Parking> {
    return this.http.post<Parking>(this.apiUrl, parking);
  }

  updateParking(id: string, parking: Partial<ParkingRequest>): Observable<Parking> {
    return this.http.patch<Parking>(`${this.apiUrl}/${id}`, parking);
  }

  deleteParking(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

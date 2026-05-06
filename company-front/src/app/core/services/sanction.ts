import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Sanction } from '../models/sanction.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SanctionService {
  private apiUrl = `${environment.apiUrl}/sanctions`;

  constructor(private http: HttpClient) {}

  getSanctionsByCompany(companyId: number): Observable<Sanction[]> {
    return this.http.get<Sanction[]>(`${this.apiUrl}/company/${companyId}`);
  }

  paySanction(id: number): Observable<Sanction> {
    return this.http.patch<Sanction>(`${this.apiUrl}/${id}/pay`, {});
  }

  deleteSanction(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

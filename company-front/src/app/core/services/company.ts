import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface Company {
  id: number;
  name: string;
  phone: string;
  email: string;
  cif: string;
  registerDay: string;
}

@Injectable({
  providedIn: 'root'
})
export class CompanyService {
  private apiUrl = `${environment.apiUrl}/companies`;

  constructor(private http: HttpClient) {}

  getCompanyById(id: number): Observable<Company> {
    return this.http.get<Company>(`${this.apiUrl}/${id}`);
  }

  updateCompany(id: number, company: Partial<Company>): Observable<Company> {
    return this.http.put<Company>(`${this.apiUrl}/${id}`, company);
  }
}

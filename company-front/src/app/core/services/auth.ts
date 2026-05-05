import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap, switchMap } from 'rxjs';
import { LoginRequest, RegisterRequest, AuthResponse, UserResponse } from '../models/auth.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly apiUrl = 'http://localhost:8080/api';
  
  // Signal to store current user state
  currentUser = signal<AuthResponse | null>(null);

  constructor(private http: HttpClient) {}

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/auth/company/login`, credentials).pipe(
      tap(response => this.currentUser.set(response))
    );
  }

  register(data: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/auth/company/register`, data).pipe(
      tap(response => this.currentUser.set(response))
    );
  }

  logout() {
    this.currentUser.set(null);
    localStorage.removeItem('token');
  }

  isAuthenticated(): boolean {
    return this.currentUser() !== null;
  }
}

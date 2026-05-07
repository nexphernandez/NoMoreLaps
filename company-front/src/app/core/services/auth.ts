import { Injectable, signal, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap, switchMap } from 'rxjs';
import { LoginRequest, RegisterRequest, AuthResponse, UserResponse } from '../models/auth.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly apiUrl = environment.apiUrl;
  
  // Signal to store current user state
  currentUser = signal<AuthResponse | null>(this.getStoredUser());

  constructor(private http: HttpClient) {}

  private getStoredUser(): AuthResponse | null {
    const userJson = localStorage.getItem('user');
    return userJson ? JSON.parse(userJson) : null;
  }

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/auth/company/login`, credentials).pipe(
      tap(response => {
        this.currentUser.set(response);
        localStorage.setItem('user', JSON.stringify(response));
        localStorage.setItem('token', response.token);
      })
    );
  }

  register(data: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/auth/company/register`, data).pipe(
      tap(response => {
        this.currentUser.set(response);
        localStorage.setItem('user', JSON.stringify(response));
        localStorage.setItem('token', response.token);
      })
    );
  }

  logout() {
    this.currentUser.set(null);
    localStorage.removeItem('user');
    localStorage.removeItem('token');
  }

  isAuthenticated(): boolean {
    return this.currentUser() !== null;
  }
}

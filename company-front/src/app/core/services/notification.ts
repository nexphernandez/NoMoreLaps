import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject, tap } from 'rxjs';
import { Notification } from '../models/notification.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private apiUrl = `${environment.apiUrl}/notifications`;
  
  private unreadCountSubject = new BehaviorSubject<number>(0);
  unreadCount$ = this.unreadCountSubject.asObservable();

  private notificationsSubject = new BehaviorSubject<Notification[]>([]);
  notifications$ = this.notificationsSubject.asObservable();

  constructor(private http: HttpClient) {}

  getNotifications(companyId: number): Observable<Notification[]> {
    return this.http.get<Notification[]>(`${this.apiUrl}/company/${companyId}`).pipe(
      tap(notifications => {
        this.notificationsSubject.next(notifications);
        const unread = notifications.filter(n => !n.isRead).length;
        this.unreadCountSubject.next(unread);
      })
    );
  }

  markAsRead(id: number): Observable<Notification> {
    return this.http.patch<Notification>(`${this.apiUrl}/${id}/read`, {}).pipe(
      tap(updatedNote => {
        const currentCount = this.unreadCountSubject.value;
        if (currentCount > 0) {
          this.unreadCountSubject.next(currentCount - 1);
        }
        
        const currentNotes = this.notificationsSubject.value;
        const updatedNotes = currentNotes.map(n => n.id === id ? { ...n, isRead: true } : n);
        this.notificationsSubject.next(updatedNotes);
      })
    );
  }

  markAllAsRead(companyId: number): Observable<void> {
    return this.http.patch<void>(`${this.apiUrl}/company/${companyId}/read-all`, {}).pipe(
      tap(() => {
        this.unreadCountSubject.next(0);
        const currentNotes = this.notificationsSubject.value;
        const updatedNotes = currentNotes.map(n => ({ ...n, isRead: true }));
        this.notificationsSubject.next(updatedNotes);
      })
    );
  }

  deleteNotification(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}

import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NotificationService } from '../../../core/services/notification';
import { AuthService } from '../../../core/services/auth';
import { Notification } from '../../../core/models/notification.model';

@Component({
  selector: 'app-notifications',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './notifications.html',
  styleUrls: ['./notifications.css'],
})
export class NotificationsComponent implements OnInit {
  notifications: Notification[] = [];
  loading = true;
  private pollInterval: any;

  constructor(
    private notificationService: NotificationService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.checkUserAndLoad();
  }

  private checkUserAndLoad() {
    const user = this.authService.currentUser();
    if (user && user.companyId) {
      this.loadNotifications(user.companyId);
      if (this.pollInterval) clearInterval(this.pollInterval);
      this.pollInterval = setInterval(() => {
        const currentUser = this.authService.currentUser();
        if (currentUser && currentUser.companyId) {
          this.loadNotifications(currentUser.companyId, false);
        }
      }, 5000);
    } else {
      setTimeout(() => this.checkUserAndLoad(), 1000);
    }
  }

  ngOnDestroy() {
    if (this.pollInterval) {
      clearInterval(this.pollInterval);
    }
  }

  loadNotifications(companyId: number, showLoading: boolean = true) {
    if (showLoading) this.loading = true;
    this.notificationService.getNotifications(companyId).subscribe({
      next: (data) => {
        // Filter only UNREAD and requested types, sort by date
        this.notifications = data
          .filter(n => !n.isRead && (n.type === 'RESERVATION' || n.type === 'SANCTION'))
          .sort((a, b) => new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime());
        
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error fetching notifications:', err);
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  markAsRead(notification: Notification) {
    if (notification.isRead) return;
    
    this.notificationService.markAsRead(notification.id).subscribe({
      next: () => {
        this.notifications = this.notifications.filter(n => n.id !== notification.id);
        this.cdr.detectChanges();
      }
    });
  }

  deleteNotification(id: number) {
    this.notificationService.deleteNotification(id).subscribe({
      next: () => {
        this.notifications = this.notifications.filter(n => n.id !== id);
        this.cdr.detectChanges();
      }
    });
  }

  formatDate(dateStr: string): string {
    return new Date(dateStr).toLocaleString();
  }
}

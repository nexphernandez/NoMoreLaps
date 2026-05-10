import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Sidebar } from '../../../shared/components/sidebar/sidebar';
import { AuthService } from '../../../core/services/auth';
import { NotificationService } from '../../../core/services/notification';
import { Observable } from 'rxjs';
import { Notification } from '../../../core/models/notification.model';
import { SearchService } from '../../../core/services/search';
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-dashboard-layout',
  standalone: true,
  imports: [CommonModule, RouterModule, Sidebar],
  templateUrl: './dashboard-layout.html',
  styleUrls: ['./dashboard-layout.css'],
})
export class DashboardLayout implements OnInit {
  authService = inject(AuthService);
  notificationService = inject(NotificationService);
  searchService = inject(SearchService);
  
  user = this.authService.currentUser;
  unreadCount$: Observable<number> = this.notificationService.unreadCount$;
  recentNotifications$: Observable<Notification[]> = this.notificationService.notifications$;
  
  showNotifications = false;

  ngOnInit() {
    const userData = this.user();
    if (userData && userData.companyId) {
      this.notificationService.getNotifications(userData.companyId).subscribe();
    }
  }

  toggleNotifications() {
    this.showNotifications = !this.showNotifications;
    if (this.showNotifications) {
      const userData = this.user();
      if (userData && userData.companyId) {
        this.notificationService.markAllAsRead(userData.companyId).subscribe();
      }
    }
  }

  markAsRead(id: number) {
    this.notificationService.markAsRead(id).subscribe();
  }

  onSearch(event: Event) {
    const query = (event.target as HTMLInputElement).value;
    this.searchService.updateSearchQuery(query);
  }
}

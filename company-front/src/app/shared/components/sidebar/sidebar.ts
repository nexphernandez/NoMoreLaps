import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth';
import { NotificationService } from '../../../core/services/notification';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.html',
  styleUrls: ['./sidebar.css'],
})
export class Sidebar implements OnInit {
  unreadCount$: Observable<number>;

  constructor(
    private router: Router, 
    private authService: AuthService,
    private notificationService: NotificationService
  ) {
    this.unreadCount$ = this.notificationService.unreadCount$;
  }

  ngOnInit() {
    const user = this.authService.currentUser();
    if (user && user.companyId) {
      this.notificationService.getNotifications(user.companyId).subscribe();
    }
  }

  onLogout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}

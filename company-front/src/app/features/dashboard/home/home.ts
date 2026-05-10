import { Component, effect, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ParkingService } from '../../../core/services/parking';
import { ReservationService } from '../../../core/services/reservation';
import { AuthService } from '../../../core/services/auth';
import { forkJoin } from 'rxjs';
import { Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="home-container">
      <h1 class="page-title">Dashboard</h1>
      
      <div class="stats-grid">
        <div class="stat-card glass-card">
          <span class="stat-icon material-symbols-outlined">local_parking</span>
          <div class="stat-info">
            <span class="stat-label">Active Parkings</span>
            <span class="stat-value">{{ stats().activeParkings }}</span>
          </div>
        </div>

        <div class="stat-card glass-card">
          <span class="stat-icon material-symbols-outlined">calendar_today</span>
          <div class="stat-info">
            <span class="stat-label">Today's Reservations</span>
            <span class="stat-value">{{ stats().todayReservations }}</span>
          </div>
        </div>

        <div class="stat-card glass-card">
          <span class="stat-icon material-symbols-outlined">payments</span>
          <div class="stat-info">
            <span class="stat-label">Total Revenue</span>
            <span class="stat-value">{{ stats().totalRevenue | currency:'EUR' }}</span>
          </div>
        </div>

        <div class="stat-card glass-card">
          <span class="stat-icon material-symbols-outlined">receipt_long</span>
          <div class="stat-info">
            <span class="stat-label">Pending Payments</span>
            <span class="stat-value">{{ stats().pendingPayments }}</span>
          </div>
        </div>
      </div>

      <div class="recent-activity glass-card">
        <h3>Recent Activity</h3>
        <div class="activity-list">
          @for (item of recentActivity(); track item.time) {
            <div class="activity-item">
              <span class="activity-type" [class.reservation]="item.type === 'reservation'">
                <span class="material-symbols-outlined">{{ item.type === 'reservation' ? 'event' : 'monetization_on' }}</span>
              </span>
              <div class="activity-details">
                <p class="activity-text">{{ item.message }}</p>
                <span class="activity-time">{{ item.time | date:'short' }}</span>
              </div>
            </div>
          } @empty {
            <p style="color: var(--text-secondary); margin-top: 1rem;">No new activity to display.</p>
          }
        </div>
      </div>
    </div>
  `,
  styles: [`
    .home-container {
      animation: fadeIn 0.5s ease-out;
    }
    .page-title {
      font-size: 2rem;
      font-weight: 700;
      margin-bottom: 2rem;
    }
    .stats-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
      gap: 1.5rem;
      margin-bottom: 2rem;
    }
    .stat-card {
      padding: 1.5rem;
      display: flex;
      align-items: center;
      gap: 1.5rem;
      transition: var(--transition);
    }
    .stat-card:hover {
      transform: translateY(-5px);
    }
    .stat-icon {
      font-size: 2rem;
      padding: 1rem;
      background: rgba(255, 255, 255, 0.05);
      border-radius: 1rem;
    }
    .stat-info {
      display: flex;
      flex-direction: column;
    }
    .stat-label {
      font-size: 0.875rem;
      color: var(--text-secondary);
    }
    .stat-value {
      font-size: 1.5rem;
      font-weight: 700;
      color: var(--text-primary);
    }
    .recent-activity {
      padding: 2rem;
      min-height: 300px;
    }
    .activity-list {
      margin-top: 1.5rem;
      display: flex;
      flex-direction: column;
      gap: 1rem;
    }
    .activity-item {
      display: flex;
      align-items: center;
      gap: 1rem;
      padding: 1rem;
      background: rgba(255, 255, 255, 0.03);
      border-radius: 0.75rem;
    }
    .activity-type {
      font-size: 1.25rem;
      padding: 0.5rem;
      border-radius: 0.5rem;
      background: rgba(255, 255, 255, 0.05);
    }
    .activity-type.reservation {
      color: var(--accent);
    }
    .activity-details {
      display: flex;
      flex-direction: column;
    }
    .activity-text {
      font-weight: 500;
      margin: 0;
    }
    .activity-time {
      font-size: 0.75rem;
      color: var(--text-secondary);
    }
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(10px); }
      to { opacity: 1; transform: translateY(0); }
    }
  `]
})
export class Home {
  stats = signal({
    activeParkings: 0,
    todayReservations: 0,
    totalRevenue: 0,
    pendingPayments: 0
  });

  recentActivity = signal<any[]>([]);

  private router = inject(Router);
  private parkingService = inject(ParkingService);
  private reservationService = inject(ReservationService);
  private authService = inject(AuthService);

  constructor() {
    effect(() => {
      const user = this.authService.currentUser();
      if (user && user.companyId) {
        this.loadDashboardData(user.companyId);
      }
    });

    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      filter(event => {
        const url = (event as NavigationEnd).urlAfterRedirects;
        return url === '/dashboard' || url === '/dashboard/home';
      })
    ).subscribe(() => {
      const user = this.authService.currentUser();
      if (user && user.companyId) {
        this.loadDashboardData(user.companyId);
      }
    });
  }

  loadDashboardData(companyId: number) {
    forkJoin({
      parkings: this.parkingService.getParkingsByCompany(companyId),
      reservations: this.reservationService.getReservationsByCompany(companyId)
    }).subscribe({
      next: (data) => {
        if (data.parkings && data.reservations) {
          this.calculateStats(data);
          this.processActivity(data);
        }
      },
      error: (err) => console.error('Error loading dashboard data:', err)
    });
  }

  calculateStats(data: any) {
    const today = new Date().toLocaleDateString();
    const parkings = data.parkings || [];
    const reservations = data.reservations || [];

    const newStats = {
      activeParkings: parkings.length,
      todayReservations: reservations.filter((r: any) => {
        if (!r.startTime) return false;
        return new Date(r.startTime).toLocaleDateString() === today;
      }).length,
      totalRevenue: reservations
        .filter((r: any) => r.paid && r.price)
        .reduce((acc: number, r: any) => acc + (r.price || 0), 0),
      pendingPayments: reservations.filter((r: any) => !r.paid).length
    };

    this.stats.set(newStats);
  }

  processActivity(data: any) {
    const activities = [
      ...data.reservations.map((r: any) => ({
        type: 'reservation',
        time: r.creationTime,
        message: `New reservation: ${r.userName} at ${r.parkingName}`
      })),
      ...data.reservations.filter((r: any) => (r.sanctionPrice || 0) > 0).map((r: any) => ({
        type: 'billing',
        time: r.startTime,
        message: `Sanction detected: ${r.userName} (${r.sanctionPrice}€)`
      }))
    ];

    this.recentActivity.set(activities
      .sort((a, b) => new Date(b.time).getTime() - new Date(a.time).getTime())
      .slice(0, 5));
  }
}

import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Reservation } from '../../../core/models/reservation.model';

@Component({
  selector: 'app-reservation-row',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="reservation-card">
      <div class="reservation-row glass-card" (click)="toggleExpand()">
        <div class="res-info">
          <span class="res-user">{{ reservation.userName }}</span>
          <span class="res-parking">
            <span class="material-symbols-outlined" style="font-size: 1rem; vertical-align: middle;">location_on</span> 
            {{ reservation.parkingName }}
          </span>
        </div>
        
        <div class="res-time">
          <span class="res-date">
            <span class="material-symbols-outlined" style="font-size: 1rem; vertical-align: middle;">calendar_today</span> 
            {{ formatDate(reservation.startTime) }}
          </span>
          <span class="res-hours">
            <span class="material-symbols-outlined" style="font-size: 1rem; vertical-align: middle;">schedule</span> 
            {{ formatTime(reservation.startTime, reservation.endTime) }}
          </span>
        </div>

        <div class="res-status">
          <span class="status-badge" [ngClass]="reservation.state.toLowerCase()">{{ getStatusLabel() }}</span>
        </div>

        <div class="res-billing">
          <div class="price-box">
            <span class="total">{{ reservation.price | currency:'EUR' }}</span>
            <span class="paid-indicator" [class.is-paid]="reservation.paid">
              <span class="material-symbols-outlined" style="font-size: 0.9rem; vertical-align: middle;">
                {{ reservation.paid ? 'check_circle' : 'pending' }}
              </span>
              {{ reservation.paid ? 'Pagado' : 'Pendiente' }}
            </span>
          </div>
        </div>

        <div class="res-actions">
          <button class="action-btn">{{ expanded ? 'Cerrar' : 'Detalles' }}</button>
        </div>
      </div>

      <div class="details-pane glass-card" *ngIf="expanded">
        <div class="details-grid">
          <div class="detail-item">
            <span class="label">ID Reserva</span>
            <span class="value">#{{ reservation.id }}</span>
          </div>
          <div class="detail-item">
            <span class="label">ID Plaza</span>
            <span class="value">Plaza {{ reservation.parkingSpotId }}</span>
          </div>
          <div class="detail-item">
            <span class="label">Precio Base</span>
            <span class="value">{{ reservation.basePrice | currency:'EUR' }}</span>
          </div>
          <div class="detail-item" *ngIf="reservation.sanctionPrice > 0">
            <span class="label">Sanciones</span>
            <span class="value sanction">{{ reservation.sanctionPrice | currency:'EUR' }}</span>
          </div>
          <div class="detail-item">
            <span class="label">Fecha Creación</span>
            <span class="value">{{ reservation.creationTime | date:'medium' }}</span>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .reservation-card {
      margin-bottom: 1rem;
    }
    .reservation-row {
      display: grid;
      grid-template-columns: 1.5fr 1fr 1fr 1fr 0.5fr;
      align-items: center;
      padding: 1.25rem 2rem;
      border-radius: 1rem;
      transition: var(--transition);
      gap: 1rem;
      cursor: pointer;
    }
    .reservation-row:hover {
      background: rgba(255, 255, 255, 0.05);
      transform: translateY(-2px);
    }
    .res-info, .res-time {
      display: flex;
      flex-direction: column;
      gap: 0.25rem;
    }
    .res-user {
      font-weight: 600;
      color: var(--text-primary);
    }
    .res-parking, .res-date, .res-hours {
      font-size: 0.85rem;
      color: var(--text-secondary);
    }
    .status-badge {
      font-size: 0.75rem;
      padding: 0.35rem 0.8rem;
      border-radius: 2rem;
      font-weight: 600;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }
    .active { background: rgba(129, 140, 248, 0.1); color: var(--accent-secondary); border: 1px solid rgba(129, 140, 248, 0.2); }
    .completed { background: rgba(16, 185, 129, 0.1); color: var(--success); border: 1px solid rgba(16, 185, 129, 0.2); }
    .cancelled { background: rgba(244, 63, 94, 0.1); color: var(--danger); border: 1px solid rgba(244, 63, 94, 0.2); }
    
    .action-btn {
      background: transparent;
      border: 1px solid var(--glass-border);
      color: var(--text-secondary);
      padding: 0.5rem 1rem;
      border-radius: 0.5rem;
      cursor: pointer;
      font-size: 0.85rem;
      transition: var(--transition);
    }
    .action-btn:hover {
      border-color: var(--accent-primary);
      color: var(--accent-primary);
    }

    .res-billing {
      display: flex;
      justify-content: center;
    }
    .price-box {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 0.25rem;
    }
    .total {
      font-weight: 700;
      color: var(--text-primary);
    }
    .paid-indicator {
      font-size: 0.7rem;
      color: var(--danger);
    }
    .paid-indicator.is-paid {
      color: var(--success);
    }

    .details-pane {
      margin-top: 0.5rem;
      padding: 1.5rem 2rem;
      border-radius: 1rem;
      background: rgba(15, 23, 42, 0.4);
      animation: slideDown 0.3s ease-out;
    }

    @keyframes slideDown {
      from { opacity: 0; transform: translateY(-10px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .details-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
      gap: 2rem;
    }

    .detail-item {
      display: flex;
      flex-direction: column;
      gap: 0.4rem;
    }

    .detail-item .label {
      font-size: 0.75rem;
      color: var(--text-secondary);
      text-transform: uppercase;
      letter-spacing: 1px;
    }

    .detail-item .value {
      font-size: 0.95rem;
      font-weight: 500;
      color: var(--text-primary);
    }

    .value.sanction {
      color: var(--danger);
    }

    @media (max-width: 768px) {
      .reservation-row {
        grid-template-columns: 1fr 1fr;
        gap: 1.5rem;
      }
      .res-actions {
        grid-column: span 2;
        text-align: right;
      }
      .details-grid {
        grid-template-columns: 1fr 1fr;
      }
    }
  `],
})
export class ReservationRow {
  @Input() reservation!: Reservation;
  expanded = false;

  toggleExpand() {
    this.expanded = !this.expanded;
  }

  getStatusLabel() {
    switch (this.reservation.state) {
      case 'ACTIVE': return 'Activa';
      case 'COMPLETED': return 'Finalizada';
      case 'CANCELLED': return 'Cancelada';
      default: return this.reservation.state;
    }
  }

  formatDate(dateStr: string): string {
    return new Date(dateStr).toLocaleDateString();
  }

  formatTime(start: string, end: string): string {
    const s = new Date(start).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    const e = new Date(end).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
    return `${s} - ${e}`;
  }
}

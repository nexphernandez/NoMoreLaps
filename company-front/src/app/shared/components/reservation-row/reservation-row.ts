import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-reservation-row',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="reservation-row glass-card">
      <div class="res-info">
        <span class="res-user">{{ userName }}</span>
        <span class="res-parking">📍 {{ parkingName }}</span>
      </div>
      
      <div class="res-time">
        <span class="res-date">📅 {{ date }}</span>
        <span class="res-hours">⏰ {{ time }}</span>
      </div>

      <div class="res-status">
        <span class="status-badge" [ngClass]="status.toLowerCase()">{{ status }}</span>
      </div>

      <div class="res-actions">
        <button class="action-btn">Detalles</button>
      </div>
    </div>
  `,
  styles: [`
    .reservation-row {
      display: grid;
      grid-template-columns: 1.5fr 1fr 1fr 0.5fr;
      align-items: center;
      padding: 1.25rem 2rem;
      margin-bottom: 1rem;
      border-radius: 1rem;
      transition: var(--transition);
      gap: 1rem;
    }
    .reservation-row:hover {
      background: rgba(255, 255, 255, 0.05);
      transform: scale(1.01);
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
    .pendiente { background: rgba(129, 140, 248, 0.1); color: var(--accent-secondary); border: 1px solid rgba(129, 140, 248, 0.2); }
    .finalizada { background: rgba(16, 185, 129, 0.1); color: var(--success); border: 1px solid rgba(16, 185, 129, 0.2); }
    .cancelada { background: rgba(244, 63, 94, 0.1); color: var(--danger); border: 1px solid rgba(244, 63, 94, 0.2); }
    
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

    @media (max-width: 768px) {
      .reservation-row {
        grid-template-columns: 1fr 1fr;
        gap: 1.5rem;
      }
      .res-actions {
        grid-column: span 2;
        text-align: right;
      }
    }
  `],
})
export class ReservationRow {
  @Input() userName = '';
  @Input() parkingName = '';
  @Input() date = '';
  @Input() time = '';
  @Input() status = 'Pendiente';
}

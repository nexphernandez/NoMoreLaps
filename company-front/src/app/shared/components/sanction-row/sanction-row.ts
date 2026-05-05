import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-sanction-row',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="sanction-row glass-card">
      <div class="sanc-info">
        <span class="sanc-user">{{ userName }}</span>
        <span class="sanc-parking">📍 {{ parkingName }}</span>
      </div>
      
      <div class="sanc-details">
        <span class="sanc-date">📅 {{ date }}</span>
        <span class="sanc-amount">💰 {{ amount }}€</span>
      </div>

      <div class="sanc-status">
        <span class="status-badge" [ngClass]="status.toLowerCase()">{{ status }}</span>
      </div>

      <div class="sanc-actions">
        <button class="action-btn">Emitir Factura</button>
      </div>
    </div>
  `,
  styles: [`
    .sanction-row {
      display: grid;
      grid-template-columns: 1.5fr 1fr 1fr 0.8fr;
      align-items: center;
      padding: 1.25rem 2rem;
      margin-bottom: 1rem;
      border-radius: 1rem;
      transition: var(--transition);
      gap: 1rem;
    }
    .sanction-row:hover {
      background: rgba(255, 255, 255, 0.05);
      transform: scale(1.01);
    }
    .sanc-info, .sanc-details {
      display: flex;
      flex-direction: column;
      gap: 0.25rem;
    }
    .sanc-user {
      font-weight: 600;
      color: var(--text-primary);
    }
    .sanc-parking, .sanc-date {
      font-size: 0.85rem;
      color: var(--text-secondary);
    }
    .sanc-amount {
      font-weight: 700;
      color: var(--danger);
    }
    .status-badge {
      font-size: 0.75rem;
      padding: 0.35rem 0.8rem;
      border-radius: 2rem;
      font-weight: 600;
      text-transform: uppercase;
      letter-spacing: 0.5px;
    }
    .pendiente { background: rgba(244, 63, 94, 0.1); color: var(--danger); border: 1px solid rgba(244, 63, 94, 0.2); }
    .pagada { background: rgba(16, 185, 129, 0.1); color: var(--success); border: 1px solid rgba(16, 185, 129, 0.2); }
    
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
      .sanction-row {
        grid-template-columns: 1fr 1fr;
        gap: 1.5rem;
      }
      .sanc-actions {
        grid-column: span 2;
        text-align: right;
      }
    }
  `]
})
export class SanctionRow {
  @Input() userName = '';
  @Input() parkingName = '';
  @Input() date = '';
  @Input() amount = 0;
  @Input() status = 'Pendiente';
}

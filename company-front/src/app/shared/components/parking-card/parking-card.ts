import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-parking-card',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="parking-card glass-card">
      <div class="card-header">
        <h3 class="parking-name">{{ name }}</h3>
        <span class="status-badge" [class.active]="status === 'Open'">{{ status }}</span>
      </div>
      
      <p class="parking-address">
        <span class="material-symbols-outlined" style="font-size: 1.1rem; vertical-align: middle;">location_on</span> 
        {{ address }}
      </p>
      
      <div class="card-footer">
        <div class="stat-group">
          <div class="stat">
            <span class="label">Capacity</span>
            <span class="value">{{ capacity }} spots</span>
          </div>
          <div class="stat">
            <span class="label">Price</span>
            <span class="value">{{ price | number:'1.2-2' }} €/h</span>
          </div>
        </div>
        
        <div class="actions">
          <button class="icon-btn edit" (click)="onEditClick($event)" title="Edit">
            <span class="material-symbols-outlined">edit</span>
          </button>
          <button class="icon-btn delete" (click)="onDeleteClick($event)" title="Delete">
            <span class="material-symbols-outlined">delete</span>
          </button>
          <button class="manage-btn" (click)="onManageClick($event)">Manage</button>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .parking-card {
      padding: 1.5rem;
      transition: var(--transition);
      cursor: pointer;
    }
    .parking-card:hover {
      transform: translateY(-5px);
      border-color: var(--accent-primary);
    }
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: flex-start;
      margin-bottom: 1rem;
    }
    .parking-name {
      font-size: 1.25rem;
      font-weight: 700;
      color: var(--text-primary);
    }
    .status-badge {
      font-size: 0.75rem;
      padding: 0.25rem 0.75rem;
      border-radius: 1rem;
      background: rgba(244, 63, 94, 0.1);
      color: var(--danger);
      border: 1px solid rgba(244, 63, 94, 0.2);
    }
    .status-badge.active {
      background: rgba(16, 185, 129, 0.1);
      color: var(--success);
      border: 1px solid rgba(16, 185, 129, 0.2);
    }
    .parking-address {
      font-size: 0.9rem;
      color: var(--text-secondary);
      margin-bottom: 1.5rem;
    }
    .card-footer {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
    .stat-group {
      display: flex;
      gap: 1.5rem;
    }
    .stat {
      display: flex;
      flex-direction: column;
    }
    .label {
      font-size: 0.75rem;
      color: var(--text-secondary);
    }
    .value {
      font-weight: 600;
      color: var(--text-primary);
    }
    .actions {
      display: flex;
      gap: 0.5rem;
      align-items: center;
    }
    .icon-btn {
      background: rgba(255, 255, 255, 0.05);
      border: 1px solid var(--glass-border);
      color: var(--text-primary);
      width: 32px;
      height: 32px;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 0.5rem;
      cursor: pointer;
      transition: var(--transition);
      font-size: 0.9rem;
    }
    .icon-btn:hover {
      background: rgba(255, 255, 255, 0.1);
    }
    .icon-btn.edit:hover {
      border-color: var(--accent-primary);
      color: var(--accent-primary);
    }
    .icon-btn.delete:hover {
      border-color: var(--danger);
      color: var(--danger);
    }
    .manage-btn {
      background: transparent;
      border: 1px solid var(--accent-primary);
      color: var(--accent-primary);
      padding: 0.5rem 1rem;
      border-radius: 0.5rem;
      font-size: 0.875rem;
      cursor: pointer;
      transition: var(--transition);
    }
    .manage-btn:hover {
      background: var(--accent-primary);
      color: white;
    }
  `],
})
export class ParkingCard {
  @Input() id!: number;
  @Input() name = '';
  @Input() address = '';
  @Input() capacity = 0;
  @Input() status = 'Open';
  @Input() price = 2.00;

  @Output() edit = new EventEmitter<number>();
  @Output() delete = new EventEmitter<number>();
  @Output() manage = new EventEmitter<number>();

  onEditClick(event: Event) {
    event.stopPropagation();
    this.edit.emit(this.id);
  }

  onDeleteClick(event: Event) {
    event.stopPropagation();
    this.delete.emit(this.id);
  }

  onManageClick(event: Event) {
    event.stopPropagation();
    this.manage.emit(this.id);
  }
}

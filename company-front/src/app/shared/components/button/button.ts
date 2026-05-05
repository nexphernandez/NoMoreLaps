import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [CommonModule],
  template: `
    <button 
      [type]="type"
      [class]="'btn-primary ' + customClass"
      [disabled]="disabled || loading"
      (click)="onClick.emit($event)"
    >
      <span *ngIf="!loading">{{ text }}</span>
      <span *ngIf="loading" class="loader">Cargando...</span>
    </button>
  `,
  styles: [`
    .loader {
      display: inline-block;
      animation: pulse 1.5s infinite;
    }
    @keyframes pulse {
      0%, 100% { opacity: 1; }
      50% { opacity: 0.5; }
    }
  `]
})
export class ButtonComponent {
  @Input() text = '';
  @Input() type: 'button' | 'submit' = 'button';
  @Input() disabled = false;
  @Input() loading = false;
  @Input() customClass = '';
  
  @Output() onClick = new EventEmitter<MouseEvent>();
}

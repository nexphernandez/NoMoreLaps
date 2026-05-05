import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="home-container">
      <h1 class="page-title">Panel de Control</h1>
      
      <div class="stats-grid">
        <div class="stat-card glass-card">
          <span class="stat-icon">🅿️</span>
          <div class="stat-info">
            <span class="stat-label">Parkings Activos</span>
            <span class="stat-value">12</span>
          </div>
        </div>

        <div class="stat-card glass-card">
          <span class="stat-icon">📅</span>
          <div class="stat-info">
            <span class="stat-label">Reservas Hoy</span>
            <span class="stat-value">84</span>
          </div>
        </div>

        <div class="stat-card glass-card">
          <span class="stat-icon">💰</span>
          <div class="stat-info">
            <span class="stat-label">Ingresos Hoy</span>
            <span class="stat-value">1.240€</span>
          </div>
        </div>

        <div class="stat-card glass-card">
          <span class="stat-icon">⚖️</span>
          <div class="stat-info">
            <span class="stat-label">Sanciones Pendientes</span>
            <span class="stat-value">5</span>
          </div>
        </div>
      </div>

      <div class="recent-activity glass-card">
        <h3>Actividad Reciente</h3>
        <p style="color: var(--text-secondary); margin-top: 1rem;">No hay actividad nueva para mostrar.</p>
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
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(10px); }
      to { opacity: 1; transform: translateY(0); }
    }
  `]
})
export class Home {}

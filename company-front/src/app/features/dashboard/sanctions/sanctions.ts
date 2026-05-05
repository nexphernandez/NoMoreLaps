import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SanctionRow } from '../../../shared/components/sanction-row/sanction-row';

@Component({
  selector: 'app-sanctions',
  standalone: true,
  imports: [CommonModule, SanctionRow],
  templateUrl: './sanctions.html',
  styleUrls: ['./sanctions.css'],
})
export class Sanctions {
  mockSanctions = [
    { user: 'Juan Pérez', parking: 'Parking Central Plaza', date: '2024-05-01', amount: 15.00, status: 'Pendiente' },
    { user: 'María García', parking: 'Parking Estación Sur', date: '2024-04-28', amount: 30.00, status: 'Pagada' },
    { user: 'Carlos Rodríguez', parking: 'Parking Puerto Marina', date: '2024-05-02', amount: 15.00, status: 'Pendiente' },
    { user: 'Ana Martínez', parking: 'Parking Central Plaza', date: '2024-05-03', amount: 45.00, status: 'Pendiente' },
    { user: 'Luis López', parking: 'Parking Estación Sur', date: '2024-04-30', amount: 15.00, status: 'Pagada' },
  ];
}

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReservationRow } from '../../../shared/components/reservation-row/reservation-row';

@Component({
  selector: 'app-reservations',
  standalone: true,
  imports: [CommonModule, ReservationRow],
  templateUrl: './reservations.html',
  styleUrls: ['./reservations.css'],
})
export class Reservations {
  mockParkings = ['Parking Central Plaza', 'Parking Estación Sur', 'Parking Puerto Marina'];
  
  mockReservations = [
    { user: 'Juan Pérez', parking: 'Parking Central Plaza', date: '2024-05-05', time: '10:00 - 12:00', status: 'Pendiente' },
    { user: 'María García', parking: 'Parking Estación Sur', date: '2024-05-05', time: '11:30 - 14:00', status: 'Finalizada' },
    { user: 'Carlos Rodríguez', parking: 'Parking Puerto Marina', date: '2024-05-04', time: '15:00 - 18:00', status: 'Cancelada' },
    { user: 'Ana Martínez', parking: 'Parking Central Plaza', date: '2024-05-05', time: '16:00 - 17:30', status: 'Pendiente' },
    { user: 'Luis López', parking: 'Parking Estación Sur', date: '2024-05-05', time: '09:00 - 10:00', status: 'Finalizada' },
  ];
}

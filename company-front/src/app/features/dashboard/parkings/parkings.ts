import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ParkingCard } from '../../../shared/components/parking-card/parking-card';

@Component({
  selector: 'app-parkings',
  standalone: true,
  imports: [CommonModule, ParkingCard],
  templateUrl: './parkings.html',
  styleUrls: ['./parkings.css'],
})
export class Parkings {
  mockParkings = [
    { name: 'Parking Central Plaza', address: 'Calle Mayor 1, Madrid', capacity: 150, status: 'Abierto' },
    { name: 'Parking Estación Sur', address: 'Av. Mediterráneo 12, Valencia', capacity: 300, status: 'Abierto' },
    { name: 'Parking Puerto Marina', address: 'Paseo Marítimo 45, Barcelona', capacity: 200, status: 'Cerrado' },
    { name: 'Parking Centro Histórico', address: 'Plaza Nueva 3, Sevilla', capacity: 80, status: 'Abierto' },
  ];

  onAddParking() {
    console.log('Opening add parking form...');
  }
}

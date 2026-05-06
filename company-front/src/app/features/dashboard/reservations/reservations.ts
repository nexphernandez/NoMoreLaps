import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReservationRow } from '../../../shared/components/reservation-row/reservation-row';
import { ReservationService } from '../../../core/services/reservation';
import { ParkingService } from '../../../core/services/parking';
import { AuthService } from '../../../core/services/auth';
import { Reservation } from '../../../core/models/reservation.model';
import { Parking } from '../../../core/models/parking.model';

@Component({
  selector: 'app-reservations',
  standalone: true,
  imports: [CommonModule, FormsModule, ReservationRow],
  templateUrl: './reservations.html',
  styleUrls: ['./reservations.css'],
})
export class Reservations {
  reservations: Reservation[] = [];
  parkings: Parking[] = [];
  loading = true;

  searchTerm = '';
  parkingFilter = 'all';
  statusFilter = 'all';

  constructor(
    private reservationService: ReservationService,
    private parkingService: ParkingService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    const user = this.authService.currentUser();
    if (user && user.companyId) {
      this.loadData(user.companyId);
    }
  }

  loadData(companyId: number) {
    this.loading = true;
    this.parkingService.getParkingsByCompany(companyId).subscribe(data => {
      this.parkings = data;
    });

    this.reservationService.getReservationsByCompany(companyId).subscribe({
      next: (data) => {
        this.reservations = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error fetching reservations:', err);
        this.loading = false;
      }
    });
  }

  get filteredReservations(): Reservation[] {
    return this.reservations.filter(res => {
      const matchesSearch = res.userName.toLowerCase().includes(this.searchTerm.toLowerCase());
      const matchesParking = this.parkingFilter === 'all' || res.parkingName === this.parkingFilter;
      
      let resStatus = res.state;
      const matchesStatus = this.statusFilter === 'all' || 
                           (this.statusFilter === 'active' && resStatus === 'ACTIVE') ||
                           (this.statusFilter === 'finished' && resStatus === 'COMPLETED') ||
                           (this.statusFilter === 'canceled' && resStatus === 'CANCELLED');

      return matchesSearch && matchesParking && matchesStatus;
    });
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

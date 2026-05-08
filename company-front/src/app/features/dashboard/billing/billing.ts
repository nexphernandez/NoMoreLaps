import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReservationService } from '../../../core/services/reservation';
import { ParkingService } from '../../../core/services/parking';
import { AuthService } from '../../../core/services/auth';
import { Reservation } from '../../../core/models/reservation.model';
import { Parking } from '../../../core/models/parking.model';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-billing',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './billing.html',
  styleUrls: ['./billing.css'],
})
export class Billing implements OnInit {
  reservations: Reservation[] = [];
  parkings: Parking[] = [];
  loading = true;

  searchTerm = '';
  parkingFilter = 'all';
  statusFilter = 'all';

  constructor(
    private reservationService: ReservationService,
    private parkingService: ParkingService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit() {
    const user = this.authService.currentUser();
    if (user && user.companyId) {
      this.loadData(user.companyId);
    } else {
      this.loading = false;
      this.cdr.detectChanges();
    }
  }

  loadData(companyId: number) {
    this.loading = true;
    forkJoin({
      parkings: this.parkingService.getParkingsByCompany(companyId),
      reservations: this.reservationService.getReservationsByCompany(companyId)
    }).subscribe({
      next: (data) => {
        this.parkings = data.parkings;
        this.reservations = data.reservations;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error fetching billing data:', err);
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  get filteredReservations(): Reservation[] {
    return this.reservations.filter(r => {
      const matchesSearch = r.userName.toLowerCase().includes(this.searchTerm.toLowerCase());
      const matchesParking = this.parkingFilter === 'all' || r.parkingName === this.parkingFilter;
      const matchesStatus = this.statusFilter === 'all' ||
        (this.statusFilter === 'paid' && r.paid) ||
        (this.statusFilter === 'pending' && !r.paid);

      return matchesSearch && matchesParking && matchesStatus;
    });
  }

  togglePayment(reservation: Reservation) {
    const newStatus = !reservation.paid;
    this.reservationService.updatePaymentStatus(reservation.id, newStatus).subscribe({
      next: (updated) => {
        reservation.paid = updated.paid;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error updating payment status:', err);
      }
    });
  }

  formatDate(dateStr: string): string {
    return new Date(dateStr).toLocaleString();
  }

  get totalCollected(): number {
    return this.reservations
      .filter(r => r.paid)
      .reduce((acc, curr) => acc + curr.price, 0);
  }

  get pendingAmount(): number {
    return this.reservations
      .filter(r => !r.paid)
      .reduce((acc, curr) => acc + curr.price, 0);
  }
}

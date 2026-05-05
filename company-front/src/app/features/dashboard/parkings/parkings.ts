import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ParkingCard } from '../../../shared/components/parking-card/parking-card';
import { ParkingService } from '../../../core/services/parking';
import { AuthService } from '../../../core/services/auth';
import { Parking } from '../../../core/models/parking.model';

@Component({
  selector: 'app-parkings',
  standalone: true,
  imports: [CommonModule, ParkingCard],
  templateUrl: './parkings.html',
  styleUrls: ['./parkings.css'],
})
export class Parkings implements OnInit {
  parkings: Parking[] = [];
  loading = true;

  constructor(
    private router: Router,
    private parkingService: ParkingService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    const user = this.authService.currentUser();
    if (user && user.companyId) {
      this.parkingService.getParkingsByCompany(user.companyId).subscribe({
        next: (data) => {
          this.parkings = data;
          this.loading = false;
        },
        error: (err) => {
          console.error('Error fetching parkings:', err);
          this.loading = false;
        }
      });
    } else {
      this.loading = false;
    }
  }

  onAddParking() {
    this.router.navigate(['/dashboard/parkings/new']);
  }
}

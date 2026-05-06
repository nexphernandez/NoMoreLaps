import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
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
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    const user = this.authService.currentUser();
    console.log('DEBUG: Current user in Parkings component:', user);
    
    if (user && user.companyId) {
      console.log('DEBUG: Fetching parkings for companyId:', user.companyId);
      this.parkingService.getParkingsByCompany(user.companyId).subscribe({
        next: (data) => {
          console.log('DEBUG: Parkings received from backend:', data);
          this.parkings = data;
          this.loading = false;
          this.cdr.detectChanges(); // Force UI update
        },
        error: (err) => {
          console.error('DEBUG: Error fetching parkings:', err);
          this.loading = false;
          this.cdr.detectChanges(); // Force UI update
        }
      });
    } else {
      console.warn('DEBUG: No user or companyId found, stopping loading.');
      this.loading = false;
      this.cdr.detectChanges(); // Force UI update
    }
  }

  onAddParking() {
    this.router.navigate(['/dashboard/parkings/new']);
  }
}

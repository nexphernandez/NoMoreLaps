import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { InputComponent } from '../../../../shared/components/input/input';
import { ButtonComponent } from '../../../../shared/components/button/button';
import { ParkingService } from '../../../../core/services/parking';
import { AuthService } from '../../../../core/services/auth';

@Component({
  selector: 'app-parking-create',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule, InputComponent, ButtonComponent],
  templateUrl: './parking-create.html',
  styleUrls: ['./parking-create.css'],
})
export class ParkingCreate implements OnInit {
  parkingForm: FormGroup;
  loading = false;
  isEditMode = false;
  parkingId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private parkingService: ParkingService,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.parkingForm = this.fb.group({
      name: ['', [Validators.required]],
      address: ['', [Validators.required]],
      latitude: [40.4168, [Validators.required]],
      longitude: [-3.7038, [Validators.required]],
      sanctionAmount: [15.00, [Validators.required, Validators.min(0)]],
      sanctionIntervalInMinutes: [30, [Validators.required, Validators.min(1)]],
      totalSpots: [10, [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.isEditMode = true;
      this.parkingId = Number(idParam);
      this.loadParking(this.parkingId);
    }
  }

  loadParking(id: number) {
    this.loading = true;

    this.parkingService.getParkingsByCompany(this.authService.currentUser()!.companyId).subscribe({
      next: (parkings) => {
        const parking = parkings.find(p => p.id === id);
        if (parking) {
          this.parkingForm.patchValue({
            name: parking.name,
            address: parking.address,
            latitude: parking.latitude,
            longitude: parking.longitude,
            sanctionAmount: parking.sanctionAmount,
            sanctionIntervalInMinutes: parking.sanctionIntervalInMinutes,
            totalSpots: parking.totalSpots
          });
        }
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading parking:', err);
        this.loading = false;
      }
    });
  }

  onSave() {
    if (this.parkingForm.invalid) return;

    const user = this.authService.currentUser();
    if (!user) return;

    this.loading = true;
    const parkingData = {
      ...this.parkingForm.value,
      companyId: user.companyId
    };

    if (this.isEditMode && this.parkingId) {
      this.parkingService.updateParking(this.parkingId, parkingData).subscribe({
        next: () => {
          this.router.navigate(['/dashboard/parkings']);
        },
        error: (err) => {
          this.loading = false;
          console.error('Error updating parking:', err);
        }
      });
    } else {
      this.parkingService.createParking(parkingData).subscribe({
        next: () => {
          this.router.navigate(['/dashboard/parkings']);
        },
        error: (err) => {
          this.loading = false;
          console.error('Error creating parking:', err);
        }
      });
    }
  }
}

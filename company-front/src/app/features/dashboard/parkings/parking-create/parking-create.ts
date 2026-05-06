import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
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
export class ParkingCreate {
  parkingForm: FormGroup;
  loading = false;

  constructor(
    private fb: FormBuilder,
    private parkingService: ParkingService,
    private authService: AuthService,
    private router: Router
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

  onSave() {
    if (this.parkingForm.invalid) return;

    const user = this.authService.currentUser();
    if (!user) return;

    this.loading = true;
    const parkingData = {
      ...this.parkingForm.value,
      companyId: user.companyId
    };

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

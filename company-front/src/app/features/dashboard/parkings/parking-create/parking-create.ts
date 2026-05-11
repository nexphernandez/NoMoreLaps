import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { InputComponent } from '../../../../shared/components/input/input';
import { ButtonComponent } from '../../../../shared/components/button/button';
import { ParkingService } from '../../../../core/services/parking';
import { AuthService } from '../../../../core/services/auth';

declare var leafletLib: any;

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
  private map: any;
  private marker: any;

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

  ngAfterViewInit() {
    this.initMap();
  }

  private initMap() {
    
    const lat = this.parkingForm.get('latitude')?.value || 28.4678;
    const lon = this.parkingForm.get('longitude')?.value || -16.2472;

    this.map = leafletLib.map('map').setView([lat, lon], 13);

    leafletLib.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap contributors'
    }).addTo(this.map);

    this.marker = leafletLib.marker([lat, lon], { draggable: false }).addTo(this.map);

    this.map.on('click', (e: any) => {
      const { lat, lng } = e.latlng;
      this.updateLocation(lat, lng);
    });
  }

  private updateLocation(lat: number, lon: number) {
    this.parkingForm.patchValue({
      latitude: parseFloat(lat.toFixed(6)),
      longitude: parseFloat(lon.toFixed(6))
    });

    if (this.marker) {
      this.marker.setLatLng([lat, lon]);
    }

    this.map.panTo([lat, lon]);

    fetch(`https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lon}`)
      .then(res => res.json())
      .then(data => {
        if (data.display_name) {
          this.parkingForm.patchValue({ address: data.display_name });
        }
      })
      .catch(err => console.error('Geocoding error:', err));
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
          
          if (this.map && this.marker) {
            this.marker.setLatLng([parking.latitude, parking.longitude]);
            this.map.setView([parking.latitude, parking.longitude], 15);
          }
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

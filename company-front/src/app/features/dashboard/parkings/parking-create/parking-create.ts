import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { InputComponent } from '../../../../shared/components/input/input';
import { ButtonComponent } from '../../../../shared/components/button/button';

@Component({
  selector: 'app-parking-create',
  standalone: true,
  imports: [CommonModule, RouterModule, InputComponent, ButtonComponent],
  templateUrl: './parking-create.html',
  styleUrls: ['./parking-create.css'],
})
export class ParkingCreate {
  constructor(private router: Router) {}

  onSave(event: any) {
    console.log('Saving parking (mock)...');
    this.router.navigate(['/dashboard/parkings']);
  }
}

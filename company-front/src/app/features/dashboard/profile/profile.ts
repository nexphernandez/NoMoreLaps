import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InputComponent } from '../../../shared/components/input/input';
import { ButtonComponent } from '../../../shared/components/button/button';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, InputComponent, ButtonComponent],
  templateUrl: './profile.html',
  styleUrls: ['./profile.css'],
})
export class Profile {
  onSave(event: any) {
    console.log('Saving profile changes (mock)...');
    // Logic for API call will be added later
  }
}

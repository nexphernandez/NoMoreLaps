import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { InputComponent } from '../../../shared/components/input/input';
import { ButtonComponent } from '../../../shared/components/button/button';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, RouterModule, InputComponent, ButtonComponent],
  templateUrl: './register.html',
  styleUrls: ['./register.css'],
})
export class Register {
  onRegister(event: any) {
    console.log('Register attempt');
    // Logic will be added in next phase
  }
}

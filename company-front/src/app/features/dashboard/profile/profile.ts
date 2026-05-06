import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InputComponent } from '../../../shared/components/input/input';
import { ButtonComponent } from '../../../shared/components/button/button';
import { CompanyService, Company } from '../../../core/services/company';
import { AuthService } from '../../../core/services/auth';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule, InputComponent, ButtonComponent],
  templateUrl: './profile.html',
  styleUrls: ['./profile.css'],
})
export class Profile implements OnInit {
  company: Partial<Company> = {
    name: '',
    cif: '',
    email: '',
    phone: ''
  };
  loading = true;

  constructor(
    private companyService: CompanyService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    const user = this.authService.currentUser();
    if (user && user.companyId) {
      this.loadCompany(user.companyId);
    }
  }

  loadCompany(id: number) {
    this.loading = true;
    this.companyService.getCompanyById(id).subscribe({
      next: (data) => {
        this.company = data;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading company:', err);
        this.loading = false;
      }
    });
  }

  onSave(event: any) {
    if (this.company.id) {
      this.companyService.updateCompany(this.company.id, this.company).subscribe({
        next: (updated) => {
          this.company = updated;
          alert('Perfil actualizado con éxito');
        },
        error: (err) => {
          console.error('Error updating profile:', err);
          alert('Error al actualizar el perfil');
        }
      });
    }
  }
}

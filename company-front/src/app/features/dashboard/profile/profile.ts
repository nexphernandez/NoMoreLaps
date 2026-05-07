import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
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
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    console.log('Profile component initialized');
    const user = this.authService.currentUser();
    console.log('Current user:', user);
    if (user && user.companyId) {
      this.loadCompany(user.companyId);
    } else {
      console.warn('No companyId found for user');
      this.loading = false;
      this.cdr.detectChanges();
    }
  }

  loadCompany(id: number) {
    console.log('Loading company data for ID:', id);
    this.loading = true;
    this.companyService.getCompanyById(id).subscribe({
      next: (data) => {
        console.log('Company data received:', data);
        this.company = data;
        this.loading = false;
        this.cdr.detectChanges();
        console.log('Loading set to false, UI should update');
      },
      error: (err) => {
        console.error('Error loading company:', err);
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  onSave(event: any) {
    if (this.company.id) {
      this.companyService.updateCompany(this.company.id, this.company).subscribe({
        next: (updated) => {
          this.company = updated;
          this.cdr.detectChanges();
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

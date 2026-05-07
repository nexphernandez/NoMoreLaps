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
  activeTab: 'general' | 'security' | 'billing' | 'notifications' = 'general';
  
  company: Partial<Company> = {
    name: '',
    cif: '',
    email: '',
    phone: ''
  };

  passwordData = {
    currentPassword: '',
    newPassword: '',
    confirmPassword: ''
  };

  loading = true;

  constructor(
    private companyService: CompanyService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    const user = this.authService.currentUser();
    if (user && user.companyId) {
      this.loadCompany(user.companyId);
    } else {
      this.loading = false;
      this.cdr.detectChanges();
    }
  }

  setTab(tab: any) {
    this.activeTab = tab;
    this.cdr.detectChanges();
  }

  loadCompany(id: number) {
    this.loading = true;
    this.companyService.getCompanyById(id).subscribe({
      next: (data) => {
        this.company = data;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
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
          alert('Perfil actualizado con éxito');
        },
        error: (err) => {
          alert('Error al actualizar el perfil');
        }
      });
    }
  }

  onChangePassword(event: any) {
    if (this.passwordData.newPassword !== this.passwordData.confirmPassword) {
      alert('Las contraseñas nuevas no coinciden');
      return;
    }

    const user = this.authService.currentUser();
    if (user && user.companyId) {

      this.authService.changePassword(user.userId || 0, this.passwordData.currentPassword, this.passwordData.newPassword)
        .subscribe({
          next: () => {
            alert('Contraseña actualizada con éxito');
            this.passwordData = { currentPassword: '', newPassword: '', confirmPassword: '' };
            this.cdr.detectChanges();
          },
          error: (err) => {
            alert('Error al cambiar la contraseña: ' + (err.error?.message || 'Verifica tu contraseña actual'));
          }
        });
    }
  }
}

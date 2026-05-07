import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CompanyService } from '../../../core/services/company';
import { AuthService } from '../../../core/services/auth';

@Component({
  selector: 'app-developer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './developer.html',
  styleUrls: ['./developer.css'],
})
export class Developer implements OnInit {
  apiKey = '';
  showKey = false;
  loading = true;

  constructor(
    private companyService: CompanyService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    const user = this.authService.currentUser();
    if (user && user.companyId) {
      this.loadApiKey(user.companyId);
    } else {
      this.loading = false;
    }
  }

  loadApiKey(companyId: number) {
    this.loading = true;
    this.companyService.getCompanyById(companyId).subscribe({
      next: (company) => {
        this.apiKey = company.apiKey || 'Sin clave generada';
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error loading API Key:', err);
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  toggleKey() {
    this.showKey = !this.showKey;
  }

  copyKey() {
    if (!this.apiKey || this.apiKey === 'Sin clave generada') return;
    navigator.clipboard.writeText(this.apiKey);
    alert('Clave de API copiada al portapapeles');
  }

  onRegenerate() {
    const user = this.authService.currentUser();
    if (!user || !user.companyId) return;

    if (confirm('¿Estás seguro de que deseas regenerar tu clave de API? Todas las integraciones actuales dejarán de funcionar inmediatamente.')) {
      this.loading = true;
      this.companyService.regenerateApiKey(user.companyId).subscribe({
        next: (company) => {
          this.apiKey = company.apiKey;
          this.loading = false;
          this.showKey = true;
          this.cdr.detectChanges();
          alert('Clave de API regenerada con éxito');
        },
        error: (err) => {
          console.error('Error regenerating API Key:', err);
          this.loading = false;
          this.cdr.detectChanges();
          alert('Error al regenerar la clave de API');
        }
      });
    }
  }
}

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
  companyId: number | null = null;
  showKey = false;
  loading = true;
  swaggerUrl = 'https://merchant-determined-hampton-handhelds.trycloudflare.com/swagger-ui/index.html?urls.primaryName=CompanyAPI';

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
        this.apiKey = company.apiKey || 'No key generated';
        this.companyId = company.id;
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
    if (!this.apiKey || this.apiKey === 'No key generated') return;
    navigator.clipboard.writeText(this.apiKey);
    alert('API Key copied to clipboard');
  }

  onRegenerate() {
    const user = this.authService.currentUser();
    if (!user || !user.companyId) return;

    if (confirm('Are you sure you want to regenerate your API key? All current integrations will stop working immediately.')) {
      this.loading = true;
      this.companyService.regenerateApiKey(user.companyId).subscribe({
        next: (company) => {
          this.apiKey = company.apiKey;
          this.loading = false;
          this.showKey = true;
          this.cdr.detectChanges();
          alert('API Key successfully regenerated');
        },
        error: (err) => {
          console.error('Error regenerating API Key:', err);
          this.loading = false;
          this.cdr.detectChanges();
          alert('Error regenerating API Key');
        }
      });
    }
  }
}

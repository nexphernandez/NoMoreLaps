import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SanctionRow } from '../../../shared/components/sanction-row/sanction-row';
import { SanctionService } from '../../../core/services/sanction';
import { ParkingService } from '../../../core/services/parking';
import { AuthService } from '../../../core/services/auth';
import { Sanction } from '../../../core/models/sanction.model';
import { Parking } from '../../../core/models/parking.model';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-sanctions',
  standalone: true,
  imports: [CommonModule, FormsModule, SanctionRow],
  templateUrl: './sanctions.html',
  styleUrls: ['./sanctions.css'],
})
export class Sanctions implements OnInit {
  sanctions: Sanction[] = [];
  parkings: Parking[] = [];
  loading = true;

  searchTerm = '';
  parkingFilter = 'all';
  statusFilter = 'all';

  constructor(
    private sanctionService: SanctionService,
    private parkingService: ParkingService,
    private authService: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    const user = this.authService.currentUser();
    if (user && user.companyId) {
      this.loadData(user.companyId);
    } else {
      this.loading = false;
      this.cdr.detectChanges();
    }
  }

  loadData(companyId: number) {
    this.loading = true;
    forkJoin({
      parkings: this.parkingService.getParkingsByCompany(companyId),
      sanctions: this.sanctionService.getSanctionsByCompany(companyId)
    }).subscribe({
      next: (data) => {
        this.parkings = data.parkings;
        this.sanctions = data.sanctions;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error fetching sanctions data:', err);
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  get filteredSanctions(): Sanction[] {
    return this.sanctions.filter(s => {
      const matchesSearch = s.userName.toLowerCase().includes(this.searchTerm.toLowerCase());
      const matchesParking = this.parkingFilter === 'all' || s.parkingName === this.parkingFilter;
      const matchesStatus = this.statusFilter === 'all' || 
                           (this.statusFilter === 'paid' && s.paid) ||
                           (this.statusFilter === 'pending' && !s.paid);

      return matchesSearch && matchesParking && matchesStatus;
    });
  }

  formatDate(dateStr: string): string {
    return new Date(dateStr).toLocaleDateString();
  }

  get totalCollected(): number {
    return this.sanctions
      .filter(s => s.paid)
      .reduce((acc, curr) => acc + curr.amount, 0);
  }

  get pendingCount(): number {
    return this.sanctions.filter(s => !s.paid).length;
  }
}

import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { ParkingCard } from '../../../shared/components/parking-card/parking-card';
import { ParkingService } from '../../../core/services/parking';
import { AuthService } from '../../../core/services/auth';
import { Parking } from '../../../core/models/parking.model';
import { SearchService } from '../../../core/services/search';

@Component({
  selector: 'app-parkings',
  standalone: true,
  imports: [CommonModule, FormsModule, ParkingCard],
  templateUrl: './parkings.html',
  styleUrls: ['./parkings.css'],
})
export class Parkings implements OnInit {
  parkings: Parking[] = [];
  loading = true;
  searchTerm = '';
  statusFilter = 'all';

  constructor(
    private router: Router,
    private parkingService: ParkingService,
    private authService: AuthService,
    private searchService: SearchService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.searchService.searchQuery$.subscribe(query => {
      this.searchTerm = query;
      this.cdr.detectChanges();
    });

    const user = this.authService.currentUser();
    console.log('DEBUG: Current user in Parkings component:', user);
    
    if (user && user.companyId) {
      console.log('DEBUG: Fetching parkings for companyId:', user.companyId);
      this.parkingService.getParkingsByCompany(user.companyId).subscribe({
        next: (data) => {
          console.log('DEBUG: Parkings received from backend:', data);
          this.parkings = data;
          this.loading = false;
          this.cdr.detectChanges(); 
        },
        error: (err) => {
          console.error('DEBUG: Error fetching parkings:', err);
          this.loading = false;
          this.cdr.detectChanges(); 
        }
      });
    } else {
      console.warn('DEBUG: No user or companyId found, stopping loading.');
      this.loading = false;
      this.cdr.detectChanges();
    }
  }

  get filteredParkings(): Parking[] {
    return this.parkings.filter(parking => {
      const matchesSearch = 
        parking.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        parking.address.toLowerCase().includes(this.searchTerm.toLowerCase());
      
      const matchesStatus = 
        this.statusFilter === 'all' || 
        (this.statusFilter === 'active' && parking.status === 'Open') ||
        (this.statusFilter === 'inactive' && parking.status === 'Closed');

      return matchesSearch && matchesStatus;
    });
  }

  onAddParking() {
    this.router.navigate(['/dashboard/parkings/new']);
  }

  onEdit(id: number) {
    this.router.navigate(['/dashboard/parkings/edit', id]);
  }

  onDelete(id: number) {
    if (confirm('Are you sure you want to delete this parking? This action cannot be undone.')) {
      this.parkingService.deleteParking(id).subscribe({
        next: () => {
          this.parkings = this.parkings.filter(p => p.id !== id);
          this.cdr.detectChanges();
          alert('Parking deleted successfully');
        },
        error: (err) => {
          console.error('Error deleting parking:', err);
          alert('Error deleting parking');
        }
      });
    }
  }

  onManage(id: number) {
    this.router.navigate(['/dashboard/parkings/edit', id]);
  }
}

import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login/login';
import { Register } from './features/auth/register/register';
import { DashboardLayout } from './features/dashboard/dashboard-layout/dashboard-layout';
import { Home } from './features/dashboard/home/home';
import { Parkings } from './features/dashboard/parkings/parkings';
import { ParkingCreate } from './features/dashboard/parkings/parking-create/parking-create';
import { Reservations } from './features/dashboard/reservations/reservations';
import { Developer } from './features/dashboard/developer/developer';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: Register },
  { 
    path: 'dashboard', 
    component: DashboardLayout,
    children: [
      { path: '', component: Home },
      { path: 'parkings', component: Parkings },
      { path: 'parkings/new', component: ParkingCreate },
      { path: 'reservations', component: Reservations },
      { path: 'developer', component: Developer },
      { path: '**', redirectTo: '' }
    ]
  },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }
];

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-developer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './developer.html',
  styleUrls: ['./developer.css'],
})
export class Developer {
  apiKey = 'nml_live_51PkL02I9u7XzR4w9q8v7b6n5m4a3s2d1f';
  showKey = false;

  toggleKey() {
    this.showKey = !this.showKey;
  }

  copyKey() {
    navigator.clipboard.writeText(this.apiKey);
    // In a real app, we would show a toast notification here
    console.log('API Key copied to clipboard');
  }

  onRegenerate() {
    if (confirm('¿Estás seguro de que deseas regenerar tu clave de API? Todas las integraciones actuales dejarán de funcionar.')) {
      console.log('Regenerating API Key...');
    }
  }
}

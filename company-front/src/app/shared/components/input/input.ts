import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-input',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="input-group">
      <label *ngIf="label" [for]="id">{{ label }}</label>
      <input 
        [id]="id"
        [type]="type"
        [placeholder]="placeholder"
        class="input-field"
        [value]="value"
        (input)="onInputChange($event)"
      >
    </div>
  `
})
export class InputComponent {
  @Input() id = 'input-' + Math.random().toString(36).substring(2, 9);
  @Input() label = '';
  @Input() type = 'text';
  @Input() placeholder = '';
  @Input() value = '';

  onInputChange(event: any) {
    this.value = event.target.value;
  }
}

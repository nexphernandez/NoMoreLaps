import { Component, Input, forwardRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ControlValueAccessor, NG_VALUE_ACCESSOR, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-input',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  template: `
    <div class="input-group">
      @if (label) {
        <label [for]="id">{{ label }}</label>
      }
      <input 
        [id]="id"
        [type]="type"
        [placeholder]="placeholder"
        class="input-field"
        [class.input-error]="error"
        [value]="value"
        (input)="handleInput($event)"
        (blur)="onTouched()"
        [disabled]="disabled"
      >
      @if (error) {
        <span class="error-hint">{{ error }}</span>
      }
    </div>
  `,
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => InputComponent),
      multi: true
    }
  ]
})
export class InputComponent implements ControlValueAccessor {
  @Input() id = 'input-' + Math.random().toString(36).substring(2, 9);
  @Input() label = '';
  @Input() type = 'text';
  @Input() placeholder = '';
  @Input() error: string | null = null;
  
  @Input() value: any = '';
  @Input() disabled = false;

  onChange: any = () => {};
  onTouched: any = () => {};

  writeValue(value: any): void {
    this.value = value;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this.disabled = isDisabled;
  }

  handleInput(event: any): void {
    const val = event.target.value;
    this.value = val;
    this.onChange(val);
    this.onTouched();
  }
}

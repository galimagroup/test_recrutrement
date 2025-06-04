import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import {CommonModule} from "@angular/common";

@Component({
  standalone: true,
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css'],
  imports: [CommonModule, ReactiveFormsModule],
})
export class ContactComponent {
  contactForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    message: ['', [Validators.required, Validators.maxLength(300)]]
  });

  constructor(private fb: FormBuilder) {}

  onSubmit() {
    if (this.contactForm.valid) {
      alert('Demande de contact envoyée avec succès');
      this.contactForm.reset();
    }
  }
}

import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Message } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { MessagesModule } from 'primeng/messages';

@Component({
  selector: 'app-contact-form',
  template: `
  <p-messages *ngIf="showNotification"
        [(value)]="messages" 
        [showTransitionOptions]="'800ms'" 
        [hideTransitionOptions]="'800ms'" 
        [enableService]="false" />

   <h1 class="text-center">Demande de contact</h1>
   <div class="container">
      <form [formGroup]="contactForm" (ngSubmit)="onSave()">
        <div class="form-field">
          <label for="name">Email*</label>
          <input pInputText
            type="email"
            id="name"
            name="name"
            formControlName="email"
            required>
            <div class="error">{{getErrorMessage("email")}}</div>
        </div>
        <div class="form-field">
          <label for="description">Message*</label>
          <textarea pInputTextarea 
            id="description"
            name="description"
            formControlName="message"
            rows="5" 
            cols="30">
          </textarea>
          <div class="error">{{getErrorMessage("message")}}</div>
        </div>   
        <div class="flex justify-content-between">
          <p-button type="button" (click)="onCancel()" label="Annuler" severity="help"/>
          <p-button type="submit" [disabled]="!contactForm.valid" label="Envoyer" severity="success"/>
        </div>
      </form>
   </div>
  `,
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule,
    ButtonModule,
    InputTextModule,
    InputNumberModule,
    InputTextareaModule,
    DropdownModule,
    MessagesModule
  ],
  styleUrl: './contact-form.component.css'
})
export class ContactFormComponent {

  contactForm: FormGroup;

  showNotification = false;

  messages: Message[] = [
    { severity: 'success', summary: '', detail: 'Demande de contact envoyée avec succès' },
  ];

  constructor(private fb: FormBuilder) {
    this.contactForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      message: ['', [Validators.required, Validators.maxLength(300)]]
    });
  }

  onCancel() {
    this.contactForm.reset();
  }

  onSave() {
    if (this.contactForm.valid) {
      this.showNotification = true;
    } 
  }

  getErrorMessage(field: string): string {
    const control: AbstractControl | null = this.contactForm.get(field);

    if (!control) return '';

    if (control.hasError('required')) {
      return `Le champ ${field} est obligatoire.`;
    }

    if (control.hasError('email')) {
      return `L'adresse email n'est pas valide.`;
    }

    if (control.hasError('maxlength')) {
      return `Le message ne doit pas dépasser 300 caractères.`;
    }

    return '';
  }

}

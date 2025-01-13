import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ContactService } from '../../contact.service';
import { Contact } from '../../../../models/contact';

@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.component.html',
  styleUrl: './contact-form.component.scss'
})
export class ContactFormComponent implements OnInit {
  contactForm: FormGroup;
  submitted = false;
  isSubmitting = false; // Pour gérer l'état d'envoi
  successMessage = '';
  errorMessage = '';

  constructor(private fb: FormBuilder, private contactService: ContactService) {
    // Initialisation du formulaire
    this.contactForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      message: ['', [Validators.required, Validators.minLength(10)]],
    });
  }

  ngOnInit(): void {
  }

  // Soumettre le formulaire
  onSubmit(): void {
    this.submitted = true;

    if (this.contactForm.valid) {
      this.isSubmitting = true;
      const contact: Contact = this.contactForm.value;

      this.contactService.addContact(contact).subscribe({
        next: (response) => {
          this.successMessage = 'Votre message a été envoyé avec succès !';
          this.contactForm.reset(); // Réinitialise le formulaire
          this.submitted = false; // Réinitialise l'état de validation
        },
        error: (err) => {
          console.error('Erreur lors de l\'envoi du message :', err);
          this.errorMessage = 'Une erreur est survenue. Veuillez réessayer plus tard.';
        },
        complete: () => {
          this.isSubmitting = false; // Réinitialise l'état d'envoi
        },
      });
    }
  }
}

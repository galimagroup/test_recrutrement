import { Component, inject } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ContactService } from '../data-access/contact.service';
import { ContactMessage } from '../data-access/contact.model';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { ButtonModule } from 'primeng/button';
import { MessageService } from 'primeng/api';
import { ToastModule } from 'primeng/toast';

@Component({
    selector: 'app-contact',
    standalone: true,
    imports: [
        FormsModule,
        InputTextModule,
        InputTextareaModule,
        ButtonModule,
        ToastModule
    ],
    template: `
        <div class="m-4">
            <h1>Contact</h1>
            <p-toast></p-toast>

            <form #contactForm="ngForm" (ngSubmit)="onSubmit(contactForm)" class="flex flex-column gap-4">
                <div class="flex flex-column gap-2">
                    <label for="email">Email*</label>
                    <input
                        type="email"
                        pInputText
                        id="email"
                        name="email"
                        [(ngModel)]="message.email"
                        required
                        email
                        #emailInput="ngModel">
                    @if (emailInput.invalid && (emailInput.dirty || emailInput.touched)) {
                        <small class="text-red-500">
                            Email invalide
                        </small>
                    }
                </div>

                <div class="flex flex-column gap-2">
                    <label for="message">Message* (300 caractères max)</label>
                    <textarea
                        pInputTextarea
                        id="message"
                        name="message"
                        [(ngModel)]="message.message"
                        required
                        maxlength="300"
                        rows="5"
                        #messageInput="ngModel">
                    </textarea>
                    <small class="text-gray-600">
                        {{message.message?.length || 0}}/300 caractères
                    </small>
                    @if (messageInput.invalid && (messageInput.dirty || messageInput.touched)) {
                        <small class="text-red-500">
                            Message requis
                        </small>
                    }
                </div>

                <div>
                    <p-button
                        type="submit"
                        label="Envoyer"
                        [loading]="sending"
                        [disabled]="contactForm.invalid || sending">
                    </p-button>
                </div>
            </form>
        </div>
    `
})
export class ContactComponent {
    private readonly contactService = inject(ContactService);
    private readonly messageService = inject(MessageService);

    public message: ContactMessage = {
        email: '',
        message: ''
    };

    public sending = false;

    onSubmit(form: NgForm) {
        if (form.invalid) return;

        this.sending = true;
        this.contactService.sendMessage(this.message).subscribe({
            next: () => {
                this.messageService.add({
                    severity: 'success',
                    summary: 'Succès',
                    detail: 'Demande de contact envoyée avec succès'
                });
                form.resetForm();
                this.sending = false;
            },
            error: () => {
                this.messageService.add({
                    severity: 'error',
                    summary: 'Erreur',
                    detail: 'Une erreur est survenue lors de l\'envoi du message'
                });
                this.sending = false;
            }
        });
    }
}

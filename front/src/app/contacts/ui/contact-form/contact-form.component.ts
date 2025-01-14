import { Component, inject, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { MessageService } from 'primeng/api';
import { ButtonModule } from "primeng/button";
import { InputTextModule } from "primeng/inputtext";
import { InputTextareaModule } from 'primeng/inputtextarea';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-contact-form',
  standalone: true,
  imports: [
    FormsModule, ReactiveFormsModule, ButtonModule, InputTextModule, InputTextareaModule, ToastModule
  ],
  providers: [MessageService],
  templateUrl: './contact-form.component.html',
  styleUrl: './contact-form.component.scss',
  encapsulation: ViewEncapsulation.None
})
export class ContactFormComponent {
  private readonly messageService = inject(MessageService);

  private formBuilder = inject(FormBuilder);
  public form = this.formBuilder.group({
    email: new FormControl('', [Validators.required, Validators.email]),
    msg: new FormControl('', [Validators.required, Validators.maxLength(300)]),
  });

  onSave() {
    this.messageService.add({
      severity: 'success',
      summary: 'Contact',
      detail: 'Demande de contact envoyée avec succès'
    });
  }
}

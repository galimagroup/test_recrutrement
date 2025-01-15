import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';

@Component({
  selector: 'app-contacts',
  standalone: true,
  imports: [
    FormsModule, ReactiveFormsModule, ButtonModule, InputTextModule, InputTextareaModule,
  ],
  templateUrl: './contacts.component.html',
  styleUrl: './contacts.component.css'
})
export class ContactsComponent {
  private readonly messageService = inject(MessageService);
  public messages = "";

  private formBuilder = inject(FormBuilder);
  public form = this.formBuilder.group({
    email: new FormControl('', [Validators.required, Validators.email]),
    msg: new FormControl('', [Validators.required, Validators.maxLength(300)]),
  });

  onSave() {
    this.form.reset();
    this.messages =  "Demande de contact envoyée avec succès"
  setTimeout(() => {
    this.messages = "";
  },5000)
  } ;

}

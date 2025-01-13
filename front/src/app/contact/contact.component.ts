import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { FormsModule } from "@angular/forms";
import { DialogModule } from "primeng/dialog";
import { InputTextModule } from "primeng/inputtext";
import { InputTextareaModule } from "primeng/inputtextarea";
import { ButtonModule } from "primeng/button";
import { ToastModule } from "primeng/toast";
import { MessageService } from "primeng/api";

@Component({
  selector: "app-contact",
  template: `
    <p-toast></p-toast>
    <p-dialog
      header="Formulaire de contact"
      [(visible)]="display"
      [style]="{ width: '40vw' }"
      [modal]="true"
      [closable]="true"
      (onHide)="onCloseDialog()"
    >
      <form (ngSubmit)="onSubmit()" #contactForm="ngForm" class="form">
        <div class="form-field">
          <label for="email">Email <span class="required">*</span></label>
          <input
            pInputText
            type="email"
            id="email"
            name="email"
            [(ngModel)]="email"
            required
            class="p-inputtext-sm w-full"
          />
          <small
            *ngIf="!contactForm.controls['email']?.valid && contactForm.submitted"
            class="p-error"
          >
            L'email est obligatoire.
          </small>
        </div>
        <div class="form-field">
          <label for="message">Message <span class="required">*</span></label>
          <textarea
            pInputTextarea
            id="message"
            name="message"
            rows="5"
            [(ngModel)]="message"
            required
            maxlength="300"
            class="p-inputtextarea-sm w-full"
          ></textarea>
          <small
            *ngIf="!contactForm.controls['message']?.valid && contactForm.submitted"
            class="p-error"
          >
            Le message est obligatoire.
          </small>
          <small *ngIf="message.length > 300" class="p-error">
            Le message ne peut pas contenir plus de 300 caractères.
          </small>
        </div>
        <div class="button-group">
          <p-button type="button" label="Annuler" class="p-button-secondary w-6rem" (click)="hideDialog()"></p-button>
          <p-button type="submit" label="Envoyer" class="p-button-success w-6rem" [disabled]="!contactForm.valid || message.length > 300"></p-button>
        </div>
      </form>
    </p-dialog>
  `,
  styleUrls: ["./contact.component.css"],
  standalone: true,
  providers: [MessageService],
  imports: [
    FormsModule,
    DialogModule,
    InputTextModule,
    InputTextareaModule,
    ButtonModule,
    ToastModule,
  ],
})
export class ContactComponent implements OnInit {
  public display: boolean = false;
  public email: string = "";
  public message: string = "";

  constructor(private router: Router, private messageService: MessageService) {}

  ngOnInit() {
    this.showDialog();
  }

  showDialog() {
    this.display = true;
  }

  hideDialog() {
    this.display = false;
  }

  onSubmit() {
    if (this.message.length > 300) {
      this.messageService.add({
        severity: "error",
        summary: "Erreur",
        detail: "Le message ne doit pas dépasser 300 caractères.",
        life: 3000, // Durée du message
      });
      return;
    }

    this.messageService.add({
      severity: "success",
      summary: "Succès",
      detail: "Demande de contact envoyée avec succès.",
      life: 3000, // Durée du message
    });

    this.hideDialog();

    setTimeout(() => {
      this.router.navigate(["/home"]);
    }, 3000);
  }

  onCloseDialog() {
    console.log("Le formulaire a été fermé.");
  }
}

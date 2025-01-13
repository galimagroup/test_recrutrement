import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginServiceService } from '../login-service.service';
import { User } from '../../models/user';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sign',
  templateUrl: './sign.component.html',
  styleUrl: './sign.component.scss'
})
export class SignComponent {
  signupForm: FormGroup;
  isSubmitting = false;

  constructor(private fb: FormBuilder, private authService: LoginServiceService, private toastr: ToastrService, private router: Router) {
    this.signupForm = this.fb.group({
      firstname: ['', [Validators.required]],
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]],
    }, {
      validators: this.passwordMatchValidator,
    });
  }

  // Validateur personnalisé pour les mots de passe
  passwordMatchValidator(group: FormGroup) {
    const password = group.get('password')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }

  showSuccess() {
    this.toastr.success('Inscription éffectuée avec succés !', 'Succès');
  }

  showError(msg: string) {
    this.toastr.error(msg, 'Erreur');
  }

  // Soumettre le formulaire
  onSubmit(): void {
    if (this.signupForm.invalid) {
      return;
    }

    this.isSubmitting = true;
    const user: User = {
      firstname: this.signupForm.value.firstname,
      username: this.signupForm.value.username,
      email: this.signupForm.value.email,
      password: this.signupForm.value.password,
      is_admin: false, // valeur par défaut
    };

    this.authService.register(user).subscribe({
      next: (response) => {
        this.showSuccess()
        this.signupForm.reset();
        this.goToLogin();
      },
      error: (err) => {
        console.error(err);
        this.showError("Une erreur s'est produite lors de l'inscription.");
      },
      complete: () => {
        this.isSubmitting = false;
      },
    });
  }

  goToLogin() {
    this.router.navigate(['loginsignup/login']);
  }

  goToAddProduct(){
    this.router.navigate(['shop/'])
  }
}

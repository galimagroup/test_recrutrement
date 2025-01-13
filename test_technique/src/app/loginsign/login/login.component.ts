import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginServiceService } from '../login-service.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit{

  loginForm!: FormGroup;

  email: string = '';
  password: string = '';
  errorMessage: string = '';

  public constructor(private toastr: ToastrService, private authService: LoginServiceService, private router: Router, private fb: FormBuilder){
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    
  }

  showSuccess() {
    this.toastr.success('Inscription éffectuée avec succés !', 'Succès');
  }

  showError(msg: string) {
    this.toastr.error(msg, 'Erreur');
  }

  login(): void {
    if (this.loginForm.invalid) {
      return; // Empêche la soumission si le formulaire est invalide
    }
  
    const email = this.loginForm.value.email; // Récupère l'email
    const password = this.loginForm.value.password; // Récupère le mot de passe
  
    this.authService.login(email, password).subscribe({
      next: (response) => {
        if (response.success) {
          this.authService.saveUserData(response.user, response.token);
          this.goToShop();
        } else {
          this.showError("Une erreur est survenue. !");
          this.errorMessage = response.message || 'Une erreur est survenue.';
        }
      },
      error: (err) => {
        this.showError("Login ou mot de passe incorrect !");
        this.errorMessage = 'Identifiants incorrects.';
      },
    });
  }
  

  goToSign() {
    this.router.navigate(['loginsignup/sign']);
  }

  goToShop() {
    this.router.navigate(['/shop']); 
  }

}

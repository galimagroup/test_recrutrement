import { Component, OnInit } from '@angular/core';
import { LoginServiceService } from './loginsign/login-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit{
  title = 'test_technique';
  isLoggedIn: boolean = false;

  constructor(private authService: LoginServiceService, private router: Router){}

  ngOnInit(): void {
    this.authService.loggedIn$.subscribe((loggedIn) => {
      this.isLoggedIn = loggedIn;
    });
  }

  goToProfile(): void {
    this.router.navigate(['/profile']); 
  }

  logout(): void {
    this.authService.logout(); 
    this.router.navigate(['/loginsign']); 
  }

  goToAddProduct(){
    this.router.navigate(['shop/add-products']);
  }

  goToContact(){
    this.router.navigate(['/contact']);
  }
}

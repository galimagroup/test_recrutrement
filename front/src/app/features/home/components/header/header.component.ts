import { Component } from '@angular/core';
import { PanierService } from '../../../../core/services/panier.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AsyncPipe, CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, Router } from '@angular/router';
import { AuthService } from '../../../../core/services/auth.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, AsyncPipe],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  cartItemCount$: Observable<number>;

  constructor(
    private panierService: PanierService,
    private authService: AuthService,
    private router: Router
  ) {
    this.cartItemCount$ = this.panierService.cart$.pipe(
      map(cart => cart.items.reduce((total, item) => total + item.quantity, 0))
    );
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }
}

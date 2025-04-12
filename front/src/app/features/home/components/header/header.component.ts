import { Component } from '@angular/core';
import { PanierService } from '../../../../core/services/panier.service';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { AsyncPipe, CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, AsyncPipe],
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  cartItemCount$: Observable<number>;

  constructor(private panierService: PanierService) {
    this.cartItemCount$ = this.panierService.cart$.pipe(
      map(cart => cart.items.reduce((total, item) => total + item.quantity, 0))
    );
  }
}

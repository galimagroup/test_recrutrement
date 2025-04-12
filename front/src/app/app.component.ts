import { Component } from '@angular/core';
import { map } from 'rxjs/operators';
import { PanierService } from './core/services/panier.service';
import { RouterOutlet } from '@angular/router';
import { FooterComponent } from './features/home/components/footer/footer.component';
import { HeaderComponent } from './features/home/components/header/header.component';
import { Observable } from 'rxjs';
import { SidebarComponent } from './features/home/components/sidebar/sidebar.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, FooterComponent, SidebarComponent  ],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title(title: any) {
    throw new Error('Method not implemented.');
  }
  cartItemCount$: Observable<number>;

  constructor(private panierService: PanierService) {
    this.cartItemCount$ = this.panierService.cart$.pipe(
      map(cart => cart.items.reduce((acc, item) => acc + item.quantity, 0))
    );
  }
}



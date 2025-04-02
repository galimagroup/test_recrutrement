import { Component, inject } from '@angular/core';
import { CartService } from 'app/cart/data-access/cart.service';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { DataViewModule } from 'primeng/dataview';
import { TagModule } from 'primeng/tag';
import { TooltipModule } from 'primeng/tooltip';
import { InputNumberModule } from 'primeng/inputnumber';
import { FormsModule } from '@angular/forms';
import { CartItem } from 'app/cart/data-access/cartItem.model';

@Component({
  selector: 'app-cart-list',
  standalone: true,
  imports: [
    FormsModule,
    DataViewModule,
    CardModule,
    ButtonModule,
    TagModule,
    TooltipModule,
    InputNumberModule
  ],
  templateUrl: './cart-list.component.html',
  styleUrl: './cart-list.component.css'
})
export class CartListComponent {
  private readonly cartService = inject(CartService);
    
  public readonly cart = this.cartService.cart;

  public updateQuntity(item: CartItem) {
    this.cartService.updateQuantity(item.product.id, item.quantity)
  }
}

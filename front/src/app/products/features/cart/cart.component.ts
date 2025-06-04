import { Component } from '@angular/core';
import { CartService, CartItem } from '../../data-access/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  readonly cartItems = this.cartService.items;

  constructor(private cartService: CartService) {}

  removeFromCart(productId: number): void {
    this.cartService.removeFromCart(productId);
  }

  updateQuantity(productId: number, quantity: number): void {
    this.cartService.updateQuantity(productId, quantity);
  }

  getTotal(): number {
    return this.cartItems().reduce(
        (total: number, item: CartItem) => total + (item.product.price * item.quantity),
        0
    );
  }
}

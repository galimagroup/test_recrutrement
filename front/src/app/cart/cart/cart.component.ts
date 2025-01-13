import {Component, inject} from '@angular/core';
import {Product} from "../../products/data-access/product.model";
import {CartService} from "./cart.service";
import {CardModule} from "primeng/card";
import {CommonModule} from "@angular/common";
import {ButtonModule} from "primeng/button";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CardModule, CommonModule, ButtonModule, FormsModule],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {
  private readonly cartService = inject(CartService);

  get cartItems() {
    return this.cartService.cart();
  }

  get totalPrice() {
    return this.cartService.cart().reduce((total, item) => total + item.price * item.quantity, 0);
  }


  removeFromCart(productId: number){
    this.cartService.removeFromCart(productId)
  }

  public onQuantityChange(product: Product) {
    this.cartService.updateQuantity(product.id, product.quantity);
  }


}

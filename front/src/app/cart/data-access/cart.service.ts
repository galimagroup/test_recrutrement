import { computed, Injectable, signal } from '@angular/core';
import { Product } from 'app/products/data-access/product.model';
import { CartItem } from './cartItem.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private readonly _cart = signal<CartItem[]>([]);
  public readonly cart = this._cart.asReadonly();

  public addToCart(product: Product, quantity: number = 1): void {
    this._cart.update(cart => {
      const existingItem = cart.find(item => item.product.id === product.id);
      if (existingItem) {
        return cart.map(item =>
          item.product.id === product.id
            ? { ...item, quantity: item.quantity + quantity, total: (item.quantity + quantity) * item.product.price }
            : item
        );
      }
      return [...cart, { product, quantity, total: quantity * product.price }];
    });
  }

  public updateQuantity(productId: number, quantity: number): void {
    this._cart.update(cart =>
      cart.map(item =>
        item.product.id === productId
          ? { ...item, quantity, total: quantity * item.product.price }
          : item
      ).filter(item => item.quantity > 0)
    );
  }

  public removeFromCart(productId: number): void {
    this._cart.update(cart => cart.filter(item => item.product.id !== productId));
  }

  public getCartItem(productId: number): CartItem | undefined {
    return this._cart().find(item => item.product.id === productId);
  }

  public clearCart(): void {
    this._cart.set([]);
  }
}
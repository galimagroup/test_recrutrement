import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Cart } from '../models/cart';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root',
})
export class PanierService {
  private cartSubject = new BehaviorSubject<Cart>({ items: [], total: 0 });
  public cart$ = this.cartSubject.asObservable();

  getCart() {
    return this.cart$;
  }

  
  constructor() {}

  // Ajouter un produit au panier
  addToCart(product: Product, quantity: number = 1): void {
    const currentCart = this.cartSubject.value;
    const existingItemIndex = currentCart.items.findIndex(
      (item) => item.product.id === product.id
    );

    if (existingItemIndex !== -1) {
      // Si le produit existe, on met à jour la quantité
      currentCart.items[existingItemIndex].quantity += quantity;
    } else {
      // Sinon, on ajoute un nouvel article
      currentCart.items.push({ product, quantity });
    }

    // Met à jour le panier et le total
    this.updateCart(currentCart);
  }

  // Supprimer un produit du panier
  removeFromCart(productId: number): void {
    const currentCart = this.cartSubject.value;
    currentCart.items = currentCart.items.filter(
      (item) => item.product.id !== productId
    );

    // Met à jour le panier et le total
    this.updateCart(currentCart);
  }

  // Mettre à jour la quantité d'un produit dans le panier
  updateQuantity(productId: number, quantity: number): void {
    const currentCart = this.cartSubject.value;
    const itemIndex = currentCart.items.findIndex(
      (item) => item.product.id === productId
    );

    if (itemIndex !== -1 && quantity > 0) {
      // Si l'article existe, on met à jour sa quantité
      currentCart.items[itemIndex].quantity = quantity;
    } else if (quantity <= 0) {
      // Si la quantité est 0 ou moins, on supprime l'article
      this.removeFromCart(productId);
    }

    // Met à jour le panier et le total
    this.updateCart(currentCart);
  }

  // Mise à jour du panier et calcul du total
  private updateCart(cart: Cart): void {
    const total = this.getTotal();
    // On met à jour la valeur du BehaviorSubject
    this.cartSubject.next({ ...cart, total });
  }

  // Calcul du total du panier
  getTotal(): number {
    return this.cartSubject.value.items.reduce(
      (total, item) => total + item.product.price * item.quantity,
      0
    );
  }

  // Nombre total d'articles dans le panier
  getCartItemCount(): number {
    return this.cartSubject.value.items.reduce(
      (total, item) => total + item.quantity,
      0
    );
  }
}

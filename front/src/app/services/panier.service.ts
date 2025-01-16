import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from 'app/products/data-access/product.model';
import { environment } from 'environments/environment';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PanierService {
  private cartItems: { product: Product, quantity: number }[] = [];
  private cartItemCount = new BehaviorSubject<number>(0);

  constructor(private http: HttpClient) {}

  addToCart(userId: number, productId: number): Observable<any> {
    return this.http.post(`${environment.apiUrl}/${userId}/add/${productId}`, {});
  }

  removeFromCart(userId: number, productId: number): Observable<any> {
    return this.http.delete(`${environment.apiUrl}/${userId}/remove/${productId}`);
  }

  getCart(userId: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/${userId}`);
  }

  addToPanier(product: Product): void {
    const existingItem = this.cartItems.find(item => item.product.id === product.id);
    if (existingItem) {
      existingItem.quantity++;
    } else {
      this.cartItems.push({ product, quantity: 1 });
    }
    this.cartItemCount.next(this.cartItems.reduce((total, item) => total + item.quantity, 0));
  }

  getCartItems(): { product: Product, quantity: number }[] {
    return this.cartItems;
  }

  getCartItemCount(): BehaviorSubject<number> {
    return this.cartItemCount;
  }

  removeFromPanier(productId: number): void {
    this.cartItems = this.cartItems.filter(item => item.product.id !== productId);
    this.cartItemCount.next(this.cartItems.reduce((total, item) => total + item.quantity, 0));
  }

  adjustQuantity(productId: number, quantity: number): void {
    const item = this.cartItems.find(item => item.product.id === productId);
    if (item) {
      item.quantity = quantity;
      this.cartItemCount.next(this.cartItems.reduce((total, item) => total + item.quantity, 0));
    }
  }
}

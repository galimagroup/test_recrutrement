import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Product } from 'app/products/data-access/product.model';

@Injectable({
  providedIn: 'root',
})
export class OrderService {

  private orderItems = new BehaviorSubject<{ product: Product; quantity: number }[]>([]);
  orderItems$ = this.orderItems.asObservable();

  addToOrder(product: Product) {
      const currentItems = this.orderItems.getValue();
      const index = currentItems.findIndex(item => item.product.id === product.id);

      if (index === -1) {
        // Ajouter uniquement si la quantité en stock est suffisante
        if (product.quantity > 0) {
          currentItems.push({ product, quantity: 1 });
        } else {
          alert('Produit en rupture de stock.');
        }
      } else {
        // Vérifier que la quantité dans le panier ne dépasse pas la quantité disponible
        if (currentItems[index].quantity < product.quantity) {
          currentItems[index].quantity += 1;
        } else {
          alert('Vous ne pouvez pas ajouter plus de ce Produit.');
        }
      }

      this.orderItems.next([...currentItems]);
      this.updateOrderCount();
  }

  removeFromOrder(product: Product) {
    const currentItems = this.orderItems.getValue();
    const index = currentItems.findIndex(item => item.product.id === product.id);

    if (index !== -1) {
      currentItems.splice(index, 1);
      this.orderItems.next([...currentItems]);
      this.updateOrderCount();
    }
  }

  updateQuantity(product: Product, quantity: number) {
    const currentItems = this.orderItems.getValue();
    const index = currentItems.findIndex(item => item.product.id === product.id);

    if (index !== -1) {
      if (quantity <= 0) {
        currentItems.splice(index, 1);
      } else if (quantity <= product.quantity) {
        currentItems[index].quantity = quantity;
      } else {
        alert('Quantité demandée supérieure à la disponibilité.');
      }
      this.orderItems.next([...currentItems]);
      this.updateOrderCount();
    }
  }

  getOrderItems() {
    return this.orderItems.getValue();
  }

  private orderCount = new BehaviorSubject<number>(0);
  orderCount$ = this.orderCount.asObservable();

  private updateOrderCount() {
    const count = this.orderItems.getValue().reduce((total, item) => total + item.quantity, 0);
    this.orderCount.next(count);
  }

  clearOrder() {
    this.orderItems.next([]);
    this.updateOrderCount();
  }

}

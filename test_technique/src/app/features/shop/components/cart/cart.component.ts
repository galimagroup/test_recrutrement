import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ShopService } from '../../shop.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  cartItems: any[] = [];
  selectedItem: any = null;

  constructor(private cartService: ShopService, private router: Router) {}

  ngOnInit(): void {
    this.loadCartItems();
  }

  getUserData(): any {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }

  
  loadCartItems(): void {
    const userData = this.getUserData(); // Récupère les données utilisateur
    const userId = userData?.id;
    this.cartService.getCartItemsByUserId(userId).subscribe({
      next: (data) => {
        this.cartItems = data;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des articles du panier :', err);
      }
    });
  }

  updateQuantity(item: any, increase: boolean): void {
    const newQuantity = item.quantity + (increase ? 1 : -1);
    if (newQuantity <= 0) {
      this.removeItem(item);
    } else {
      this.cartService.updateCartItem(item.id, newQuantity).subscribe(() => {
        item.quantity = newQuantity;
      });
    }
  }

  removeItem(item: any): void {
    this.cartService.deleteCartItem(item.id).subscribe(() => {
      this.cartItems = this.cartItems.filter((i) => i.id !== item.id);
    });
  }

  getTotalItems(): number {
    return this.cartItems.reduce((total, item) => total + item.quantity, 0);
  }

  getTotalPrice(): number {
    return this.cartItems.reduce((total, item) => total + item.product.price * item.quantity, 0);
  }

  showDetails(item: any): void {
    this.selectedItem = item;
  }

  getImageUrl(imageName: string): string {
    return `http://localhost:8080/image_shop/${imageName}`;
  }
}

import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Product } from 'app/products/data-access/product.model';
import { OrderService } from './service/order.service';
import { ButtonModule } from "primeng/button";
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-order',
  standalone: true,
  imports: [CommonModule,ButtonModule,RouterModule],
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss'
})
export class OrderComponent {

  orderItems: { product: Product; quantity: number }[] = [];

  constructor(private orderService: OrderService, private router: Router) {}

  ngOnInit() {
    this.orderItems = this.orderService.getOrderItems();
  }

  removeFromOrder(product: Product) {
    this.orderService.removeFromOrder(product);
    this.orderItems = this.orderService.getOrderItems();
  }

  updateQuantity(product: Product, quantity: number) {
    this.orderService.updateQuantity(product, quantity);
    this.orderItems = this.orderService.getOrderItems();
  }

  getTotalPrice() {
    return this.orderItems.reduce((total, item) => total + item.product.price * item.quantity, 0);
  }

  checkout() {
    alert('Commande validée avec succès !');
    this.orderService.clearOrder();
    this.orderItems = [];
  }

  cancelOrder() {
    if (confirm('Êtes-vous sûr de vouloir annuler la commande ?')) {
      this.orderItems = [];
      this.updateProductQuantities();
      this.router.navigate(['/products']);
      this.orderService.clearOrder();
    }
  }

  updateProductQuantities() {
    console.log('Quantités des produits mises à jour');
  }


}

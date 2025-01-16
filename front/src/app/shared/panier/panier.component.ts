import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Product } from 'app/products/data-access/product.model';
import { PanierService } from 'app/services/panier.service';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { TableModule } from 'primeng/table';
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { InputNumberModule } from 'primeng/inputnumber';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-panier',
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, CardModule, ButtonModule, TableModule,InputNumberModule,FormsModule],
  templateUrl: './panier.component.html',
  styleUrls: ['./panier.component.css']
})
export class PanierComponent implements OnInit {
  cartItems: any;
  cartItemsWithQuantity: any[] = [];

  constructor(private cartService: PanierService) {}

  ngOnInit(): void {

    this.cartItems = this.cartService.getCartItems();
    console.log(this.cartItems);
  }

  calculateQuantities(): void {
    const productCounts: { [key: number]: number } = {};

    this.cartItems.forEach((item:any) => {
      if (productCounts[item.id]) {
        productCounts[item.id]++;
      } else {
        productCounts[item.id] = 1;
      }
    });

    this.cartItemsWithQuantity = this.cartItems.map((item:any) => ({
      ...item,
      quantity: productCounts[item.id]
    }));
  }

  removeFromCart(productId: number): void {
    console.log(productId);
    this.cartService.removeFromPanier(productId);
    this.cartItems = this.cartService.getCartItems();
    this.calculateQuantities();
  }
  adjustQuantity(productId: number, quantity: number): void {
    this.cartService.adjustQuantity(productId, quantity);
    this.cartItems = this.cartService.getCartItems();
  }
  incrementQuantity(productId: number): void {
    const item = this.cartItems.find((item:any) => item.product.id === productId);
    if (item) {
      item.quantity++;
      this.cartService.adjustQuantity(productId, item.quantity);
    }
  }

  decrementQuantity(productId: number): void {
    const item = this.cartItems.find((item:any) => item.product.id === productId);
    if (item && item.quantity > 1) {
      item.quantity--;
      this.cartService.adjustQuantity(productId, item.quantity);
    }
  }
}

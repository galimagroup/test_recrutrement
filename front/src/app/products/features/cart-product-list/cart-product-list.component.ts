import { Component, OnInit, inject } from "@angular/core";
import { Router } from "@angular/router";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { RatingModule } from 'primeng/rating';
import { TableModule } from 'primeng/table';
import { TagModule } from "primeng/tag";

@Component({
  selector: 'app-cart-product-list',
  standalone: true,
  imports: [
    CardModule, ButtonModule, RatingModule, TableModule, TagModule
  ],
  templateUrl: './cart-product-list.component.html',
  styleUrl: './cart-product-list.component.scss'
})
export class CartProductListComponent implements OnInit {
  private readonly router = inject(Router);
  private readonly productsService = inject(ProductsService);

  public cartProducts: Product[] = [];

  ngOnInit() {
    this.productsService.getCartProducts().subscribe(data => {
      this.cartProducts = data;
      console.log('cart: init cartProducts: ', this.cartProducts);
    });
  }

  public onPrevPage() {
    this.router.navigate(['/products', 'list']);
  }

  public getCartProducts() {
    console.log('getCartProducts: ', this.cartProducts);
    return this.cartProducts;
  }

  public onRemoveFromCart(product: Product) {
    if (this.cartProducts.some(p => p.id === product.id)) {
      this.cartProducts = this.cartProducts.filter(p => p.id !== product.id);
      this.productsService.setCartProducts(this.cartProducts);
    }
  }

  public getImage(product: Product) {
    return `https://primefaces.org/cdn/primeng/images/demo/product/${product.image}`;
  }

  public getSeverity(product: Product) {
    let severity: "success" | "secondary" | "info" | "warning" | "danger" | "contrast" | undefined = "success";
    if (product.inventoryStatus === "LOWSTOCK") {
      severity = "warning";
    } else if (product.inventoryStatus === "OUTOFSTOCK") {
      severity = "danger";
    }
    return severity;
  }

  public getRatingArr(product: Product) {
    return Array.from(Array(product.rating).keys());
  }
}

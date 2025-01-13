import { Component, OnInit, inject, signal } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { CommonModule } from '@angular/common';
import {CartService} from "../../../cart/cart/cart.service";
import {PaginatorModule} from "primeng/paginator";

const emptyProduct: Product = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [DataViewModule, CardModule, ButtonModule, DialogModule, ProductFormComponent, CommonModule, PaginatorModule],
})
export class ProductListComponent implements OnInit {
  private readonly productsService = inject(ProductsService);
  public readonly cartService = inject(CartService)

  public readonly products = this.productsService.products;
  public filteredProducts: Product[] = [];

  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);

  public first: number = 0;
  public rowsPerPage: number = 5;

  ngOnInit() {
    this.productsService.get().subscribe(products => {
      this.filteredProducts = products;
    });
  }

  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProduct.set(product);
  }

  public onDelete(product: Product) {
    this.productsService.delete(product.id).subscribe();
  }

  public onSave(product: Product) {
    if (this.isCreation) {
      this.productsService.create(product).subscribe();
    } else {
      this.productsService.update(product).subscribe();
    }
    this.closeDialog();
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }

  public get paginatedProducts(): Product[] {
    return this.filteredProducts.slice(this.first, this.first + this.rowsPerPage);
  }

  public onPageChange(event: any) {
    this.first = event.first;
  }

  public adjustQuantity(productId: number, adjustment: number) {
    const product = this.products().find(p => p.id === productId);
    if (product) {
      product.quantity = Math.max(product.quantity + adjustment, 1);
    }
  }

  public isInCart(product: Product): boolean {
    return !!this.cartService.cart().find(p => p.id === product.id);
  }

  onAddToCart(product: Product) {
    this.cartService.addToCart(product);
  }



  public onRemoveFromCart(productId: number) {
    this.cartService.removeFromCart(productId);
  }

  public onQuantityChange(product: Product) {
    // Lorsque la quantité change, on met à jour le produit dans le panier si ce produit est déjà dans le panier
    if (this.isInCart(product)) {
      this.cartService.updateQuantity(product.id, product.quantity);
    }
  }

}

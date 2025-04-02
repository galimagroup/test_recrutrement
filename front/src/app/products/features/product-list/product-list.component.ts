import { Component, OnInit, computed, inject, signal } from "@angular/core";
import { CartService } from "app/cart/data-access/cart.service";
import { CartItem } from "app/cart/data-access/cartItem.model";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { PaginatorModule } from 'primeng/paginator';

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
  imports: [DataViewModule, CardModule, ButtonModule, DialogModule, PaginatorModule],
})
export class ProductListComponent implements OnInit {
  private readonly productsService = inject(ProductsService);

  public readonly products = this.productsService.products;

  private readonly cartService = inject(CartService);
  
  public readonly cart = this.cartService.cart;

  cartQuantities: { [productId: string]: number } = {};
    
  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);

  public readonly rowsPerPageOptions = [5, 10, 20];
  public readonly pageSize = signal(this.rowsPerPageOptions[0]);
  public readonly currentPage = signal(0);
  public readonly totalRecords = signal(0);

  public readonly paginatedProducts = computed(() => {
    if (this.products().length === 0) return [];
    const start = this.currentPage() * this.pageSize();
    const end = start + this.pageSize();
    return this.products().slice(start, end);
  });

  ngOnInit() {
    this.productsService.get().subscribe((products) => {
      this.totalRecords.set(products.length);
      this.currentPage.set(0);
    });
  }

  public onPageChange(event: any) {
    this.pageSize.set(event.rows);
    this.currentPage.set(event.page);
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

  public onAddToCart(product: Product, quantity?: number) {
    this.cartService.addToCart(product);

    this.cartQuantities[product.id] = this.getCartQuantity(product);
    
    if (quantity) {
      this.cartQuantities[product.id] = quantity;
    }
  }

  public onRemoveFromCart(productId: number) {
    this.cartService.removeFromCart(productId);
  }

 getCartQuantity(product: Product): number {
    const cartItem = this.cart().find(item => item.product.id === product.id);
    return cartItem ? cartItem.quantity : 0;
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }

}

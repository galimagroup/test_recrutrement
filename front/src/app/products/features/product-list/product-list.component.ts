import { Component, OnInit, inject, signal } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { CartService } from "../../data-access/cart.service";
import { FormsModule } from "@angular/forms";
import { PaginatorModule } from 'primeng/paginator';
import { CommonModule } from "@angular/common";



interface ProductFilters {
  name: string;
  category: string;
  status: string;
}
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
  imports: [
    DataViewModule,
    CardModule,
    ButtonModule,
    DialogModule,
    ProductFormComponent,
    DropdownModule,
    InputTextModule,
    FormsModule,
    PaginatorModule,CommonModule
  ],
})
export class ProductListComponent implements OnInit {
  public readonly editedProduct = signal<Product>(emptyProduct);
  private readonly productsService = inject(ProductsService);
   public readonly cartService = inject(CartService);
  public readonly products = this.productsService.products;
  public readonly filteredProducts = signal<Product[]>([]);
  public isDialogVisible = false;
  public isCreation = false;
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



  // Pagination
  public first = 0;
  public rows = 5;

  // Filtres
  public filters: ProductFilters = {
    name: '',
    category: '',
    status: ''
  };

  public readonly categories = [
    { label: 'Tous', value: '' },
    { label: 'Accessories', value: 'Accessories' },
    { label: 'Fitness', value: 'Fitness' },
    { label: 'Clothing', value: 'Clothing' },
    { label: 'Electronics', value: 'Electronics' }
  ];

  public readonly statuses = [
    { label: 'Tous', value: '' },
    { label: 'En stock', value: 'INSTOCK' },
    { label: 'Stock faible', value: 'LOWSTOCK' },
    { label: 'Rupture', value: 'OUTOFSTOCK' }
  ];

  ngOnInit() {
    this.productsService.get().subscribe(() => {
      this.applyFilters();
    });
  }



  public applyFilters() {
    let filtered = this.products();

    if (this.filters.name) {
      filtered = filtered.filter(product =>
        product.name.toLowerCase().includes(this.filters.name.toLowerCase())
      );
    }

    if (this.filters.category) {
      filtered = filtered.filter(product =>
        product.category === this.filters.category
      );
    }

    if (this.filters.status) {
      filtered = filtered.filter(product =>
        product.inventoryStatus === this.filters.status
      );
    }

    this.filteredProducts.set(filtered);
  }
  public onPageChange(event: any) {
    this.first = event.first;
    this.rows = event.rows;
    this.applyFilters();
  }

  addToCart(product: Product) {
    this.cartService.addToCart(product);
}

}

import { Injectable, inject, signal } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
interface ProductListFilters {
    name: string;
    category: string;
    status: string;
  }

@Injectable({
    providedIn: "root"
}) 
export class ProductListService{
    private readonly productsService = inject(ProductsService);
    public readonly products = this.productsService.products;
  public readonly filteredProducts = signal<Product[]>([]);
    // Pagination
public first = 0;
public rows = 5;

// Filtres
public filters: ProductListFilters = {
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



}
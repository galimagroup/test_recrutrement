import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../../../core/services/product.service';
import { Product } from '../../../../core/models/product.model';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe({
      next: (products) => {
        this.products = products;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des produits:', error);
      }
    });
  }

  addToCart(product: Product): void {
    // TODO: Implémenter l'ajout au panier
    console.log('Ajouter au panier:', product);
  }

  getStatusBadgeClass(status: string): string {
    switch (status.toUpperCase()) {
      case 'INSTOCK':
        return 'bg-success';
      case 'LOWSTOCK':
        return 'bg-warning text-dark';
      case 'OUTOFSTOCK':
        return 'bg-danger';
      default:
        return 'bg-secondary';
    }
  }
}

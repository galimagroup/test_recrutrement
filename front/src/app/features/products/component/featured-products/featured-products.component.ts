import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../../../core/services/product.service';
import { PanierService } from '../../../../core/services/panier.service';
import { Product } from '../../../../core/models/product.model';

@Component({
  selector: 'app-featured-products',
  templateUrl: './featured-products.component.html',
  styleUrls: ['./featured-products.component.css']
})
export class FeaturedProductsComponent implements OnInit {
  featuredProducts: Product[] = [];
  activeSlide = 0;
  quantities: number[] = [];

  constructor(
    private productService: ProductService,
    private panierService: PanierService
  ) {}

  ngOnInit(): void {
    this.loadFeaturedProducts();
  }

  loadFeaturedProducts(): void {
    this.productService.getProducts().subscribe({
      next: (products) => {
        this.featuredProducts = products.slice(0, 3);
        this.quantities = this.featuredProducts.map(() => 1);
      },
      error: (error: any) => {
        console.error('Erreur lors du chargement des produits en vedette:', error);
      }
    });
  }

  nextSlide(): void {
    this.activeSlide = (this.activeSlide + 1) % this.featuredProducts.length;
  }

  previousSlide(): void {
    this.activeSlide = this.activeSlide === 0 
      ? this.featuredProducts.length - 1 
      : this.activeSlide - 1;
  }

  setActiveSlide(index: number): void {
    this.activeSlide = index;
  }

  getStatusBadgeClass(status: string): string {
    switch (status) {
      case 'INSTOCK': return 'badge-instock';
      case 'LOWSTOCK': return 'badge-lowstock';
      case 'OUTOFSTOCK': return 'badge-outofstock';
      default: return '';
    }
  }

  addToCart(product: Product, quantity: number): void {
    this.panierService.addToCart(product, quantity);
  }
}

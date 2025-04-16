import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../../../core/services/product.service';
import { PanierService } from '../../../../core/services/panier.service';
import { Product } from '../../../../core/models/product.model';
import { AuthService } from '../../../../core/services/auth.service';
import { Router } from '@angular/router';

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
    private panierService: PanierService,
    private authService: AuthService,
    private router: Router
  ) {}

  errorMsg: string | null = null;

  ngOnInit(): void {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login']);
      return;
    }
    this.loadFeaturedProducts();
  }

  loadFeaturedProducts(): void {
    this.productService.getProducts().subscribe({
      next: (products) => {
        this.featuredProducts = products.slice(0, 3);
        this.quantities = this.featuredProducts.map(() => 1);
      },
      error: (error: any) => {
        if (error.status === 401 || error.status === 403) {
          this.router.navigate(['/login']);
        } else {
          this.errorMsg = 'Erreur lors du chargement des produits en vedette';
          console.error('Erreur lors du chargement des produits en vedette:', error);
        }
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

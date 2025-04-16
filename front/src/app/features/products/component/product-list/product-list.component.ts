import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../../../../core/services/product.service';
import { Product } from '../../../../core/models/product.model';
import { PanierService } from '../../../../core/services/panier.service';
import { AuthService } from '../../../../core/services/auth.service';
import { Router } from '@angular/router';
import { WishlistService } from '../../../../core/services/wishlist.service';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  pagedProducts: Product[] = [];
  currentPage = 1;
  pageSize = 6;
  totalPages = 1;
  filterTerm = '';


  constructor(
    private productService: ProductService,
    private panierService: PanierService,
    private authService: AuthService,
    private router: Router,
    private wishlistService: WishlistService
  ) {}

  wishlistProductIds: number[] = [];

  errorMsg: string | null = null;

  ngOnInit(): void {
    if (!this.authService.isAuthenticated()) {
      this.router.navigate(['/login']);
      return;
    }
    this.loadProducts();
    this.loadWishlist();
  }

  loadWishlist(): void {
    this.wishlistService.getWishlist().subscribe({
      next: (items) => {
        this.wishlistProductIds = items.map(item => item.product.id);
      },
      error: (error) => {
        console.error('Erreur lors du chargement de la wishlist:', error);
      }
    });
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe({
      next: (products) => {
        this.products = products;
        this.applyFilterAndPagination();
      },
      error: (error) => {
        if (error.status === 401 || error.status === 403) {
          this.router.navigate(['/login']);
        } else {
          this.errorMsg = 'Erreur lors du chargement des produits';
          console.error('Erreur lors du chargement des produits:', error);
        }
      }
    });
  }

  applyFilterAndPagination(): void {
    let filtered = this.products;
    if (this.filterTerm.trim()) {
      filtered = this.products.filter(p =>
        p.name.toLowerCase().includes(this.filterTerm.toLowerCase())
      );
    }
    this.totalPages = Math.ceil(filtered.length / this.pageSize) || 1;
    if (this.currentPage > this.totalPages) this.currentPage = this.totalPages;
    const start = (this.currentPage - 1) * this.pageSize;
    const end = start + this.pageSize;
    this.pagedProducts = filtered.slice(start, end);
  }

  goToPage(page: number): void {
    if (page < 1 || page > this.totalPages) return;
    this.currentPage = page;
    this.applyFilterAndPagination();
  }

  onFilterChange(): void {
    this.currentPage = 1;
    this.applyFilterAndPagination();
  }

  addToCart(product: Product): void {
    this.panierService.addToCart(product);
  }

  isInWishlist(product: Product): boolean {
    return this.wishlistProductIds.includes(product.id);
  }

  toggleWishlist(product: Product): void {
    if (this.isInWishlist(product)) {
      this.wishlistService.removeFromWishlist(product.id).subscribe({
        next: () => this.loadWishlist(),
        error: (error) => console.error('Erreur lors du retrait de la wishlist:', error)
      });
    } else {
      this.wishlistService.addToWishlist(product.id).subscribe({
        next: () => this.loadWishlist(),
        error: (error) => console.error('Erreur lors de l\'ajout à la wishlist:', error)
      });
    }
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

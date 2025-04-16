import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WishlistService } from '../../core/services/wishlist.service';
import { Product } from '../../core/models/product.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-wishlist',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css']
})
export class WishlistComponent implements OnInit {
  wishlist: any[] = [];
  loading = false;
  errorMsg: string | null = null;

  constructor(private wishlistService: WishlistService, private router: Router) {}

  ngOnInit(): void {
    this.loadWishlist();
  }

  loadWishlist(): void {
    this.loading = true;
    this.wishlistService.getWishlist().subscribe({
      next: (items) => {
        this.wishlist = items;
        this.loading = false;
      },
      error: (error) => {
        this.errorMsg = 'Erreur lors du chargement de la liste d\'envie';
        this.loading = false;
      }
    });
  }

  removeFromWishlist(productId: number): void {
    this.wishlistService.removeFromWishlist(productId).subscribe({
      next: () => this.loadWishlist(),
      error: (error) => {
        this.errorMsg = 'Erreur lors du retrait de la liste d\'envie';
      }
    });
  }

  goToProduct(productId: number): void {
    this.router.navigate(['/products', productId]);
  }
}

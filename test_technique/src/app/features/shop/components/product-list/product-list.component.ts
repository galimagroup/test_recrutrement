import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ShopService } from '../../shop.service';
import { LoginServiceService } from '../../../../loginsign/login-service.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.scss'
})
export class ProductListComponent implements OnInit{

  isLoggedIn: boolean = false;
  products: any[] = [];
  currentPage = 0;
  totalPages = 0;

  // Filtres et recherche
  selectedCategory: string | null = null;
  minPrice: number | undefined;
  maxPrice: number | undefined;
  searchKeyword: string = '';
  totalProducts: number = 0;

  constructor(private authService: LoginServiceService, private shopService: ShopService, private router : Router) {}

  ngOnInit(): void {
    this.loadProducts();
    this.loadTotalProducts();
    this.authService.loggedIn$.subscribe((loggedIn) => {
      this.isLoggedIn = loggedIn;
    });
  }

  isAdmin(): boolean{
    var user = this.authService.getUserData();
    return user.admin;
  }

  goToProfile(): void {
    this.router.navigate(['/profile']); 
  }

  logout(): void {
    this.authService.logout(); 
    this.router.navigate(['/loginsign']); 
  }

  goToAddProduct(){
    this.router.navigate(['shop/add-products']);
  }

  goToEditProduct(id: number){
    this.router.navigate([`shop/add-products/${id}`]);
  }

  goToCart(){
    this.router.navigate(['shop/cart']);
  }

  goToContact(){
    this.router.navigate(['/contact']);
  }

  loadTotalProducts(): void {
    const userData = this.getUserData(); 
    const userId = userData?.id;
    this.shopService.getTotalProductsByUser(userId!).subscribe({
      next: (total) => {
        this.totalProducts = total;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération du total des produits :', err);
      },
    });
  }

  getUserData(): any {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }

  loadProducts(): void {
    this.shopService.getProducts(
      this.currentPage,
      3,
      this.selectedCategory??undefined,
      this.minPrice,
      this.maxPrice
    ).subscribe({
      next: (data) => {
        this.products = data.content;
        this.totalPages = data.totalPages;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des produits :', err);
      },
    });
  }

  filterByCategory(category: string | null): void {
    this.selectedCategory = category; // Met à jour la catégorie sélectionnée
    this.currentPage = 0; // Réinitialise à la première page
    this.loadProducts(); // Recharge les produits avec les filtres
  }
  

  getImageUrl(imageName: string): string {
    return `http://localhost:8080/image_shop/${imageName}`;
  }

  goToDetails(productId: number){
    this.router.navigate([`/shop/details-product/${productId}`]);
  }

  

  applyFilters(): void {
    this.currentPage = 0;
    this.loadProducts();
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadProducts();
    }
  }

  // Retourner à la page précédente
  prevPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadProducts();
    }
  }

  onSearchChange(): void {
    this.currentPage = 0;
    this.loadProducts();
  }

}

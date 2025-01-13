import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ShopService } from '../../shop.service';
import { CartService } from '../../cart.service';
import { ToastrService } from 'ngx-toastr';
import { LoginServiceService } from '../../../../loginsign/login-service.service';

@Component({
  selector: 'app-details-product',
  templateUrl: './details-product.component.html',
  styleUrls: ['./details-product.component.scss'],
})
export class DetailsProductComponent implements OnInit {
  product: any;
  quantity: number = 1; // Quantité par défaut

  constructor(
    private loginService: LoginServiceService,
    private router: Router,
    private toastr: ToastrService,
    private route: ActivatedRoute,
    private shopService: ShopService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    const productId = this.route.snapshot.paramMap.get('id');
    if (productId) {
      this.loadProductDetails(Number(productId));
    }
  }

  loadProductDetails(productId: number): void {
    this.shopService.getProductById(productId).subscribe({
      next: (data) => {
        this.product = data;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des détails du produit :', err);
      },
    });
  }

  getImageUrl(imageName: string): string {
    return `http://localhost:8080/image_shop/${imageName}`;
  }

  increaseQuantity(): void {
    this.quantity++;
  }

  decreaseQuantity(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  showSuccess() {
    this.toastr.success('Ajout panier effectué avec succes!', 'Succès');
  }

  showError(msg: string) {
    this.toastr.error(msg, 'Erreur');
  }

  getUserData(): any {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }

  addToCart(): void {
    const userData = this.getUserData(); 
    const userId = userData?.id;

    const cartItem = {
      "product": {
        "id": this.product.id
      },
      "quantity": this.quantity,
      "user": {id: userId}
    }

    this.cartService.addToCart(cartItem).subscribe({
      next: (response) => {
        this.showSuccess();
        this.goToProductList()
      },
      error: (err) => {
        this.showError('Erreur lors de l\'ajout au panier !');
      },
    });
  }

  goToProductList(){
      this.router.navigate(["shop/product-list"])
  }

}

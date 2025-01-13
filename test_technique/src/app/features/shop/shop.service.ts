import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Product } from '../../models/product';

@Injectable({
  providedIn: 'root',
})
export class ShopService {
  private apiUrl = `${environment.apiUrl}`; // URL de l'API

  constructor(private http: HttpClient) {}

  getProducts(page: number, size: number, category?: string, minPrice?: number, maxPrice?: number, keyword?: string): Observable<any> {
    let params: any = { page, size };
    if (category) params.category = category;
    if (minPrice) params.minPrice = minPrice;
    if (maxPrice) params.maxPrice = maxPrice;
    if (keyword) params.keyword = keyword;
  
    return this.http.get<any>(`${this.apiUrl}/products`, { params });
  }

  addToCart(cartItem: { productId: number; quantity: number }): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/cart`, cartItem);
  }
  

  // Ajoute un produit
  addProduct(product: Product, token: string): Observable<any> {
    console.log(token);
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.post(`${this.apiUrl}/products`, product, { headers });
  }

  convertFileToBase64(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        const base64String = (reader.result as string).split(',')[1]; // Supprime le préfixe "data:image/png;base64,"
        resolve(base64String);
      };
      reader.onerror = (error) => reject(error);
    });
  }

  getProductById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/products/${id}`);
  }

  getCartItemsByUserId(userId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/cart/user/${userId}`);
  }

  updateCartItem(cartId: number, quantity: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/cart/${cartId}`, { quantity });
  }

  deleteCartItem(cartId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${cartId}`);
  }

  getTotalProductsByUser(userId: number): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/cart/total-products/${userId}`);
  }

  getImageUrl(imageName: string): string {
    return `http://localhost:8080/image_shop/${imageName}`;
  }

  updateProduct(id: number, product: Product, token: string) {
    const headers = { Authorization: `Bearer ${token}` };
    return this.http.put<Product>(`${this.apiUrl}/products/${id}`, product, { headers });
  }
  
}

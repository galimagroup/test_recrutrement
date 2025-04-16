import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environment/environment';
import { Product } from '../models/product.model';

@Injectable({ providedIn: 'root' })
export class WishlistService {
  private apiUrl = environment.apiUrl + '/wishlist';

  constructor(private http: HttpClient) {}

  getWishlist(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  addToWishlist(productId: number): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/add/${productId}`, {});
  }

  removeFromWishlist(productId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/remove/${productId}`);
  }
}

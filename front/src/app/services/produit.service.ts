import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from 'app/products/data-access/product.model';
import { environment } from 'environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProduitService {

  constructor(private http:HttpClient) { }

  getAllProduits(page: number, size: number): Observable<any> {
    return this.http.get(`${environment.apiUrl}/produit/all?page=${page}&size=${size}`);
  }

  getProduitById(id: number): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/${id}`);
  }

  createProduit(produit: Product): Observable<any> {
    return this.http.post<any>(`${environment.apiUrl}/creerProduit`, produit);
  }

  updateProduit(id: number, produit: Product): Observable<any> {
    return this.http.put<any>(`${environment.apiUrl}/${id}`, produit);
  }

  deleteProduit(id: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiUrl}/${id}`);
  }
}

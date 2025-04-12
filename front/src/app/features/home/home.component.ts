import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductsModule } from '../products/products.module'; // Importez le module ici
import { ProductListComponent } from '../products/component/product-list/product-list.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ProductsModule, ProductListComponent], 
  template: `
    <app-featured-products></app-featured-products>
    <app-product-list></app-product-list>
  `
})
export class HomeComponent {}

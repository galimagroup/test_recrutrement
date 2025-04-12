import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FeaturedProductsComponent } from './component/featured-products/featured-products.component';
import { ProductService } from '../../core/services/product.service';
import { FormsModule } from '@angular/forms';
  


@NgModule({
  declarations: [FeaturedProductsComponent],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [FeaturedProductsComponent],
})
export class ProductsModule { }

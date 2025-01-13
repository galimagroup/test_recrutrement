import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductListComponent } from './components/product-list/product-list.component';
import { CartComponent } from './components/cart/cart.component';
import { DetailsProductComponent } from './components/details-product/details-product.component';
import { AddProductComponent } from './components/add-product/add-product.component';

const routes: Routes = [
  {
    path: 'product-list',
    component: ProductListComponent, 
  },
  {
    path: 'cart',
    component: CartComponent, 
  },
  {
    path: 'details-product/:id',
    component: DetailsProductComponent, 
  },
  {
    path: 'add-products',
    component: AddProductComponent, 
  },
  {
    path: 'add-products/:id',
    component: AddProductComponent, 
  },
  {
    path: '',
    redirectTo: 'product-list', 
    pathMatch: 'full', 
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ShopRoutingModule { }

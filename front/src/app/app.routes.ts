import { Routes } from '@angular/router';

import { MainLayoutComponent } from './layouts/main-layout.component';
import { AuthLayoutComponent } from './layouts/auth-layout.component';
import { LoginComponent } from './features/auth/login.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/auth/login',
    pathMatch: 'full'
  },
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      { path: 'home', loadChildren: () => import('./features/home/home.routes').then(m => m.HOME_ROUTES) },
      { path: 'contact', loadChildren: () => import('./features/contact/contact.routes').then(m => m.CONTACT_ROUTES) },
      { path: 'panier', loadChildren: () => import('./features/panier/panier.routes').then(m => m.PANIER_ROUTES) },
      { path: 'wishlist', loadChildren: () => import('./features/wishlist/wishlist.routes').then(m => m.WISHLIST_ROUTES) },
      { path: 'products', loadChildren: () => import('./features/products/products.routes').then(m => m.PRODUCTS_ROUTES) },
      // Ajoute ici d'autres routes principales si besoin
    ]
  },
  {
    path: 'auth',
    component: AuthLayoutComponent,
    children: [
      { path: 'login', component: LoginComponent }
    ]
  }
];

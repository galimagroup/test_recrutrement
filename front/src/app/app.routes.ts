import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./features/home/home.routes').then(m => m.HOME_ROUTES)
  },
  {
    path: 'contact',
    loadChildren: () => import('./features/contact/contact.routes').then(m => m.CONTACT_ROUTES)
  },
  {
    path: 'panier',
    loadChildren: () => import('./features/panier/panier.routes').then(m => m.PANIER_ROUTES)
  }
  
];

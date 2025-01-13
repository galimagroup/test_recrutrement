import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'shop',
    loadChildren: () => import('./features/shop/shop.module').then(m => m.ShopModule),
  },
  {
    path: 'loginsignup',
    loadChildren: () => import('./loginsign/loginsign.module').then(m => m.LoginsignModule),
  },
  {
    path: 'contact',
    loadChildren: () => import('./features/contact/contact.module').then(m => m.ContactModule),
  },
  {
    path: '',
    redirectTo: '/loginsignup',
    pathMatch: 'full',
  },
  {
    path: '**',
    redirectTo: '/loginsignup',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

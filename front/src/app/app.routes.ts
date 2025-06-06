import { Routes } from "@angular/router";
import { HomeComponent } from "./shared/features/home/home.component";
import {ContactComponent} from "./products/features/contact/contact.component";
import {CartComponent} from "./products/features/cart/cart.component";
import {CONTACT_ROUTES} from "./products/contact.routes";

export const APP_ROUTES: Routes = [
  {
    path: "home",
    component: HomeComponent,
  },{
    path: "home",
    component: HomeComponent,
  },
  {
    path: "products",
    loadChildren: () =>
      import("./products/products.routes").then((m) => m.PRODUCTS_ROUTES)
  },{
    path: "contact",
    loadChildren: () =>
      import("./products/contact.routes").then((m) => m.CONTACT_ROUTES)
  },
  { path: 'cart', component: CartComponent },
  { path: 'contact', component: ContactComponent },

  { path: "", redirectTo: "home", pathMatch: "full" },
];

import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { ProductListComponent } from "./features/product-list/product-list.component";
import { OrdersComponent } from "./features/orders/orders.component";

export const PRODUCTS_ROUTES: Routes = [
	{
		path: "list",
		component: ProductListComponent,
	},
  {
		path: "orders",
		component: OrdersComponent,
	},
	{ path: "**", redirectTo: "list" },
];

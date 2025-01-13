import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { ProductListComponent } from "./features/product-list/product-list.component";
import { CartProductListComponent } from "./features/cart-product-list/cart-product-list.component";

export const PRODUCTS_ROUTES: Routes = [
	{
		path: "list",
		component: ProductListComponent,
	},
	{
		path: "cart",
		component: CartProductListComponent,
	},
	{ path: "**", redirectTo: "list" },
];

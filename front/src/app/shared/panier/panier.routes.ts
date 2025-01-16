import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { PanierComponent } from "./panier.component";

export const PANIER_ROUTES: Routes = [
	{
		path: "list",
		component: PanierComponent,
	},
	{ path: "**", redirectTo: "list" },
];

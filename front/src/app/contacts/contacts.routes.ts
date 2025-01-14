import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, Routes } from "@angular/router";
import { ContactPageComponent } from "./features/contact-page/contact-page.component";

export const CONTACTS_ROUTES: Routes = [
	{
		path: "contact",
		component: ContactPageComponent,
	},
	{ path: "**", redirectTo: "contact" },
];

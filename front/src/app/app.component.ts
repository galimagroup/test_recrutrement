import {
  Component,
  inject,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { CartService } from "./products/data-access/cart.service";
import { Product } from "./products/data-access/product.model";
import { CartComponent } from "./products/ui/cart/cart.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent, CartComponent,CommonModule],
})
export class AppComponent {
  title = "ALTEN SHOP";
  public readonly cartService = inject(CartService);
  public showCart = false;

    addToCart(product: Product) {
        this.cartService.addToCart(product);
    }
}

import {
  Component,
  inject,
  OnInit,
} from "@angular/core";
import { Router, RouterModule } from "@angular/router";
import { BadgeModule } from 'primeng/badge';
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { ProductsService } from "./products/data-access/products.service";
import { Product } from "./products/data-access/product.model";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, BadgeModule, PanelMenuComponent],
})
export class AppComponent implements OnInit {
  private readonly router = inject(Router);
  private readonly productsService = inject(ProductsService);

  title = "ALTEN SHOP";
  cartProducts: Product[] = [];
  ngOnInit() {
    this.productsService.getCartProducts().subscribe(data => {
      this.cartProducts = data;
    });
  }

  public onShowCartProducts(){
    this.router.navigate(['/products', 'cart']);
  }
}

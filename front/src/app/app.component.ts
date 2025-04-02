import {
  Component,
  inject,
} from "@angular/core";
import { RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { BadgeModule } from 'primeng/badge';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { CartService } from "./cart/data-access/cart.service";
import { CartListComponent } from "./cart/features/cart-list/cart-list.component";
import { DataViewModule } from "primeng/dataview";
import { CardModule } from "primeng/card";
import { ButtonModule } from "primeng/button";
import { DialogModule } from "primeng/dialog";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [
    RouterModule,
    SplitterModule,
    ToolbarModule,
    PanelMenuComponent,
    BadgeModule,
    CartListComponent,
    DataViewModule,
    CardModule,
    ButtonModule,
    DialogModule],
})
export class AppComponent {
  title = "ALTEN SHOP";
  
  private readonly cartService = inject(CartService);

  public readonly cart = this.cartService.cart;

  public isDialogVisible = false;

  public viewCart() {
    this.isDialogVisible = true;
  }
}

import {
  Component,
} from "@angular/core";
import { Router, RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { BadgeModule } from 'primeng/badge';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { CommonModule } from "@angular/common";
import { OrderService } from "./order/service/order.service";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent, BadgeModule, CommonModule],
})
export class AppComponent {
  title = "ALTEN SHOP";
  quantPanier: number = 0;

  constructor(private router: Router, private orderService: OrderService) {}

  ngOnInit() {
    // Mettre à jour dynamiquement le badge du panier
    this.orderService.orderCount$.subscribe((count) => {
      this.quantPanier = count;
    });
  }

  onCheckOut() {
    this.router.navigate(['/order-details']);
  }

}

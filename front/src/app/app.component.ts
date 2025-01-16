import {
  Component,
  OnInit,
} from "@angular/core";
import { Router, RouterModule } from "@angular/router";
import { SplitterModule } from 'primeng/splitter';
import { ToolbarModule } from 'primeng/toolbar';
import { PanelMenuComponent } from "./shared/ui/panel-menu/panel-menu.component";
import { PanierService } from "./services/panier.service";
import { BadgeModule } from 'primeng/badge';

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.scss"],
  standalone: true,
  imports: [RouterModule, SplitterModule, ToolbarModule, PanelMenuComponent,BadgeModule],
})
export class AppComponent implements OnInit {

  
  title = "ALTEN SHOP";
  
  cartItemCount: number = 0;

  constructor(private cartService: PanierService,private router:Router) {}
  ngOnInit(): void {
    this.cartService.getCartItemCount().subscribe((count:any) => {
      this.cartItemCount = count;
    });  }
    navigateToCart(): void {
      this.router.navigate(['/cart']);
    }
}

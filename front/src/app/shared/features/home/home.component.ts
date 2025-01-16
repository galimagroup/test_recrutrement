import { Component, OnInit, inject, signal } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { PanierService } from "app/services/panier.service";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';

const emptyProduct: Product = {
  id: 0,
  code: "",
  name: "",
  description: "",
  image: "",
  category: "",
  price: 0,
  quantity: 0,
  internalReference: "",
  shellId: 0,
  inventoryStatus: "INSTOCK",
  rating: 0,
  createdAt: 0,
  updatedAt: 0,
};

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"],
  standalone: true,
  imports: [DataViewModule, CardModule, ButtonModule, DialogModule, ProductFormComponent],
})
export class HomeComponent implements OnInit{
  public readonly appTitle = "ALTEN SHOP";

  private readonly productsService = inject(ProductsService);
  private readonly cartService = inject(PanierService);

  public readonly products = this.productsService.products;

  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);
  ngOnInit(): void {
    this.productsService.get().subscribe();
  }

  // loadProducts(): void {
  //   this.produitService.getAllProduits(this.currentPage - 1, this.itemsPerPage).subscribe(data => {
  //     this.products = data.content;
  //     console.log(this.products);
  //   });
  // }
  addToCart(product: Product): void {
    this.cartService.addToPanier(product);
  }
  goToPage(page: number): void {
 //   this.currentPage = page;
    this.productsService.get().subscribe();
  }

}

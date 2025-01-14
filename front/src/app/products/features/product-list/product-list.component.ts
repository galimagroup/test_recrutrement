import { Component, OnInit, inject, signal } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { CartService } from "../../data-access/cart.service";
import { FormsModule } from "@angular/forms";
import { PaginatorModule } from 'primeng/paginator';
import { CommonModule } from "@angular/common";
import { ProductListService } from "app/products/data-access/product-list.service";


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
  selector: "app-product-list",
  templateUrl: "./product-list.component.html",
  styleUrls: ["./product-list.component.scss"],
  standalone: true,
  imports: [
    DataViewModule,
    CardModule,
    ButtonModule,
    DialogModule,
    ProductFormComponent,
    DropdownModule,
    InputTextModule,
    FormsModule,
    PaginatorModule, CommonModule
  ],
})
export class ProductListComponent implements OnInit {
 public readonly productlistService=inject(ProductListService)
  public readonly editedProduct = signal<Product>(emptyProduct);
  private readonly productsService = inject(ProductsService);
  public readonly cartService = inject(CartService);
  
  public isDialogVisible = false;
  public isCreation = false;
  public onCreate() {
    this.isCreation = true;
    this.isDialogVisible = true;
    this.editedProduct.set(emptyProduct);
  }

  public onUpdate(product: Product) {
    this.isCreation = false;
    this.isDialogVisible = true;
    this.editedProduct.set(product);
  }

  public onDelete(product: Product) {
    this.productsService.delete(product.id).subscribe();
  }

  public onSave(product: Product) {
    if (this.isCreation) {
      this.productsService.create(product).subscribe();
    } else {
      this.productsService.update(product).subscribe();
    }
    this.closeDialog();
  }

  public onCancel() {
    this.closeDialog();
  }

  private closeDialog() {
    this.isDialogVisible = false;
  }
  applyFilters(){
    return this.productlistService.applyFilters();
  }
 
  ngOnInit() {
    this.productsService.get().subscribe(() => {
      this.applyFilters();
    });
  }



  
  addToCart(product: Product) {
    this.cartService.addToCart(product);
  }

}

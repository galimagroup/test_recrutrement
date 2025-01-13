import { Component, OnInit, inject, signal } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from "app/products/data-access/products.service";
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { RatingModule } from 'primeng/rating';
import { TagModule } from 'primeng/tag';

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
    DataViewModule, FormsModule, CardModule, ButtonModule, DialogModule, 
    InputTextModule, RatingModule, TagModule, 
    ProductFormComponent
  ],
})
export class ProductListComponent implements OnInit {
  private readonly productsService = inject(ProductsService);

  public readonly products = this.productsService.products;

  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedProduct = signal<Product>(emptyProduct);
  public cartProducts: Product[] = [];
  public filterValue: string = '';

  ngOnInit() {
    this.productsService.get().subscribe();
    this.productsService.getCartProducts().subscribe(data => {
      this.cartProducts = data;
    });
  }

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

  public onAddToCart(product: Product) {
    if (!this.cartProducts.some(p => p.id === product.id)) {
      this.cartProducts.push(product)
      this.productsService.setCartProducts(this.cartProducts);
    }
  }

  public onRemoveFromCart(product: Product) {
    if (this.cartProducts.some(p => p.id === product.id)) {
      this.cartProducts = this.cartProducts.filter(p => p.id !== product.id);
      this.productsService.setCartProducts(this.cartProducts);
    }
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

  public getImage(product: Product){
    return `https://primefaces.org/cdn/primeng/images/demo/product/${product.image}`;
  }

  public getSeverity(product: Product){
    let severity: "success" | "secondary" | "info" | "warning" | "danger" | "contrast" | undefined = "success";
    if (product.inventoryStatus === "LOWSTOCK") {
      severity = "warning";
    } else if (product.inventoryStatus === "OUTOFSTOCK") {
      severity = "danger";
    }
    return severity;
  }

  public getRatingArr(product: Product){
    return Array.from(Array(product.rating).keys());
  }

  public onChangeFilter(event: any){
    console.log(event);
  }

  public getProducts(){
    return this.products().filter(p  => {
      if (this.filterValue) {
        return p.category.toLowerCase().includes(this.filterValue.toLowerCase())
          || p.name.toLowerCase().includes(this.filterValue.toLowerCase());
      }
      return true;
    });
  }
}

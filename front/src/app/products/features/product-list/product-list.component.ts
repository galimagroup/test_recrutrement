import { Component, OnInit, inject, signal, computed } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { ProductsService } from '../../data-access/products.service';
import { ProductFormComponent } from "app/products/ui/product-form/product-form.component";
import { ButtonModule } from "primeng/button";
import { CardModule } from "primeng/card";
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { BadgeModule } from 'primeng/badge';
import { CartService } from "app/products/data-access/cart.service";
import { CommonModule } from "@angular/common";

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
    imports: [DataViewModule, CardModule, ButtonModule, DialogModule, ProductFormComponent, BadgeModule,CommonModule],
})
export class ProductListComponent implements OnInit {
    private readonly productsService = inject(ProductsService);
    private readonly cartService = inject(CartService);

    public readonly products = this.productsService.products;
    public readonly cartItems = this.cartService.items;
    public readonly cartCount = computed(() => this.cartItems().length);

    public isDialogVisible = false;
    public isCartVisible = false;
    public isCreation = false;
    public readonly editedProduct = signal<Product>(emptyProduct);

    ngOnInit() {
        this.productsService.get().subscribe();
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

    public addToCart(product: Product) {
        this.cartService.addToCart(product);
    }

    removeFromCart(productId: number) {
        this.cartService.removeFromCart(productId);
    }

    public toggleCart() {
        this.isCartVisible = !this.isCartVisible;
    }
}

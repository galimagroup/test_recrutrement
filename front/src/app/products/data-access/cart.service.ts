import { Injectable, signal } from "@angular/core";
import { Product } from "app/products/data-access/product.model";

export interface CartItem {
    product: Product;
    quantity: number;
}

@Injectable({ providedIn: "root" })
export class CartService {
    private readonly _items = signal<CartItem[]>([]);

    readonly items = this._items.asReadonly(); // ✅

    addToCart(product: Product): void {
        const items = this._items();
        const existing = items.find(item => item.product.id === product.id);
        if (existing) {
            existing.quantity++;
        } else {
            items.push({ product, quantity: 1 });
        }
        this._items.set([...items]);
    }

    removeFromCart(productId: number): void {
        this._items.set(this._items().filter(item => item.product.id !== productId));
    }

    updateQuantity(productId: number, quantity: number): void {
        this._items.set(this._items().map(item =>
            item.product.id === productId ? { ...item, quantity } : item
        ));
    }
}

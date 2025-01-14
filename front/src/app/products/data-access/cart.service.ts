import { Injectable, signal } from "@angular/core";
import { CartItem } from "./cart.model";
import { Product } from "./product.model";

@Injectable({
    providedIn: 'root'
})
export class CartService {
    private readonly _items = signal<CartItem[]>([]);
    public readonly items = this._items.asReadonly();

    public readonly totalItems = signal<number>(0);

    addToCart(product: Product, quantity: number = 1) {
        this._items.update(items => {
            const existingItem = items.find(item => item.product.id === product.id);
            if (existingItem) {
                return items.map(item =>
                    item.product.id === product.id
                        ? { ...item, quantity: item.quantity + quantity }
                        : item
                );
            }
            return [...items, { product, quantity }];
        });
        this.updateTotalItems();
    }

    removeFromCart(productId: number) {
        this._items.update(items => items.filter(item => item.product.id !== productId));
        this.updateTotalItems();
    }

    updateQuantity(productId: number, quantity: number) {
        if (quantity <= 0) {
            this.removeFromCart(productId);
            return;
        }

        this._items.update(items =>
            items.map(item =>
                item.product.id === productId
                    ? { ...item, quantity }
                    : item
            )
        );
        this.updateTotalItems();
    }

    private updateTotalItems() {
        this.totalItems.set(
            this._items().reduce((total, item) => total + item.quantity, 0)
        );
    }
}

import { Injectable } from "@angular/core";
import { Product } from "app/products/data-access/product.model";
import { signal } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class CartService {
  private readonly _cart = signal<Product[]>([]);


  public readonly cart = this._cart.asReadonly();


  addToCart(product: Product) {
    const existingProduct = this._cart().find((p) => p.id === product.id);
    if (existingProduct) {
      existingProduct.quantity++;
      this._cart.set([...this._cart()]);
    } else {
      this._cart.set([...this._cart(), { ...product, quantity: 1 }]);
    }
  }


  removeFromCart(productId: number) {
    const updatedCart = this._cart().filter((product) => product.id !== productId);
    this._cart.set(updatedCart);
  }


  getTotalItems(): number {
    return this._cart().length;
  }

  updateQuantity(productId: number, quantity: number) {
    const updatedCart = this._cart().map((product) => {
      if (product.id === productId) {
        product.quantity = quantity;
      }
      return product;
    });
    this._cart.set(updatedCart);
  }

  getTotalPrice(): number {
    return this._cart().reduce((total, product) => total + (product.price * product.quantity), 0);
  }


}

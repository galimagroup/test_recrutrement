import { Injectable, signal } from "@angular/core";
import { CartItem } from "./cart.model";
import { Product } from "./product.model";

/**
 * Service de gestion du panier d'achat
 * Permet de gérer les articles dans le panier et leur quantité
 */
@Injectable({
    providedIn: 'root'
})
export class CartService {
    // Signal privé contenant la liste des articles du panier
    private readonly _items = signal<CartItem[]>([]);
    // Version en lecture seule du signal des articles accessible publiquement
    public readonly items = this._items.asReadonly();

    // Signal pour le nombre total d'articles dans le panier
    public readonly totalItems = signal<number>(0);

    /**
     * Ajoute un produit au panier
     * @param product Le produit à ajouter
     * @param quantity La quantité à ajouter (par défaut 1)
     */
    addToCart(product: Product, quantity: number = 1) {
        this._items.update(items => {
            const existingItem = items.find(item => item.product.id === product.id);
            if (existingItem) {
                // Si le produit existe déjà, on met à jour sa quantité
                return items.map(item =>
                    item.product.id === product.id
                        ? { ...item, quantity: item.quantity + quantity }
                        : item
                );
            }
            // Sinon, on ajoute un nouvel article
            return [...items, { product, quantity }];
        });
        this.updateTotalItems();
    }

    /**
     * Supprime un produit du panier
     * @param productId L'identifiant du produit à supprimer
     */
    removeFromCart(productId: number) {
        this._items.update(items => items.filter(item => item.product.id !== productId));
        this.updateTotalItems();
    }

    /**
     * Met à jour la quantité d'un produit dans le panier
     * @param productId L'identifiant du produit à mettre à jour
     * @param quantity La nouvelle quantité
     */
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

    /**
     * Met à jour le nombre total d'articles dans le panier
     * Méthode privée appelée après chaque modification du panier
     */
    private updateTotalItems() {
        this.totalItems.set(
            this._items().reduce((total, item) => total + item.quantity, 0)
        );
    }
}

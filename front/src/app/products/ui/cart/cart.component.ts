import { Component, inject } from '@angular/core';
import { CartService } from '../../data-access/cart.service';
import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { CommonModule } from '@angular/common';
@Component({
    selector: 'app-cart',
    standalone: true,
    imports: [TableModule, ButtonModule,CommonModule],
    template: `
        <div class="cart-overlay p-4">
            <h2>Mon Panier</h2>
            <p-table [value]="cartService.items()" [scrollable]="true" scrollHeight="400px">
                <ng-template pTemplate="header">
                    <tr>
                        <th>Produit</th>
                        <th>Prix</th>
                        <th>Quantité</th>
                        <th>Total</th>
                        <th></th>
                    </tr>
                </ng-template>
                <ng-template pTemplate="body" let-item>
                    <tr>
                        <td>{{item.product.name}}</td>
                        <td>{{item.product.price | currency:'EUR'}}</td>
                        <td>
                            <div class="flex align-items-center">
                                <button pButton type="button" icon="pi pi-minus"
                                        (click)="updateQuantity(item.product.id, item.quantity - 1)"
                                        class="p-button-text"></button>
                                <span class="mx-2">{{item.quantity}}</span>
                                <button pButton type="button" icon="pi pi-plus"
                                        (click)="updateQuantity(item.product.id, item.quantity + 1)"
                                        class="p-button-text"></button>
                            </div>
                        </td>
                        <td>{{item.product.price * item.quantity | currency:'EUR'}}</td>
                        <td>
                            <button pButton type="button" icon="pi pi-trash"
                                    (click)="removeFromCart(item.product.id)"
                                    class="p-button-danger p-button-text"></button>
                        </td>
                    </tr>
                </ng-template>
            </p-table>
        </div>
    `
})
export class CartComponent {
    public readonly cartService = inject(CartService);

    updateQuantity(productId: number, quantity: number) {
        this.cartService.updateQuantity(productId, quantity);
    }

    removeFromCart(productId: number) {
        this.cartService.removeFromCart(productId);
    }
}

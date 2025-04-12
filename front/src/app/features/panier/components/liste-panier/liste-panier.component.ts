import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PanierService } from '../../../../core/services/panier.service';
import { AsyncPipe, NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-liste-panier',
  standalone: true,
  imports: [CommonModule, NgFor, NgIf, AsyncPipe],
  templateUrl: './liste-panier.component.html',
  styleUrls: ['./liste-panier.component.css']
})
export class ListePanierComponent implements OnInit {
  cart$ = this.panierService.cart$;
  total$ = this.panierService.getTotal();

  constructor(private panierService: PanierService) {}

  ngOnInit(): void {}

  updateQuantity(productId: number, newQuantity: number): void {
    if (newQuantity <= 0) {
      this.removeFromCart(productId);
    } else {
      this.panierService.updateQuantity(productId, newQuantity);
    }
  }

  removeFromCart(productId: number): void {
    this.panierService.removeFromCart(productId);
  }
}

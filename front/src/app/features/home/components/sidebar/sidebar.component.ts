import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive, MatIconModule, RouterModule, MatButtonModule, MatListModule],
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  menuItems = [
    { label: 'Accueil', path: '/home', icon: 'home' },
    { label: 'Produits', path: '/products', icon: 'shopping_cart' },
    { label: 'Contact', path: '/contact', icon: 'contact_mail' },
    { label: 'Panier', path: '/panier', icon: 'shopping_cart' }
  ];
}

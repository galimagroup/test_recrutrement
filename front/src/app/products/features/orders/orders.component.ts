import { CurrencyPipe } from '@angular/common';
import { Component, inject, signal } from '@angular/core';
import { Order } from 'app/products/data-access/order.model';
import { OrderService } from 'app/products/data-access/order.service';
import { ProductFormComponent } from 'app/products/ui/product-form/product-form.component';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { DataViewModule } from 'primeng/dataview';
import { DialogModule } from 'primeng/dialog';
import { RatingModule } from 'primeng/rating';

const emptyOrder: Order ={
  id:0,
  quantity:0,
  product:{  id: 0,
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
  updatedAt: 0,}
}

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [DataViewModule, CardModule,RatingModule, ButtonModule, DialogModule, ProductFormComponent,CurrencyPipe],
  templateUrl: './orders.component.html',
  styleUrl: './orders.component.css'
})
export class OrdersComponent {
  private readonly ordersService = inject(OrderService);

  public readonly orders = this.ordersService.orders;

  public isDialogVisible = false;
  public isCreation = false;
  public readonly editedOrder = signal<Order>(emptyOrder);

  ngOnInit() {
    this.ordersService.get().subscribe();
  }

  public moreItem(order:Order){
     order.quantity++;
     this.ordersService.update(order).subscribe();
  }

  public lessItem(order:Order){
    if(order.quantity>1){
     order.quantity--;
     this.ordersService.update(order).subscribe();
    }
  }

  onDelete(order:Order){
    this.ordersService.delete(order.id).subscribe();
  }
}

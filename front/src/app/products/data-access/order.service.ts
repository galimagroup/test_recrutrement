import { Injectable, inject, signal } from "@angular/core";
import { Order } from "./order.model";
import { HttpClient } from "@angular/common/http";
import { catchError, Observable, of, tap } from "rxjs";

@Injectable({
  providedIn: "root"
}) export class OrderService{
      private readonly http = inject(HttpClient);
      private readonly path = "/api/orders";

      private readonly _orders = signal<Order[]>([]);

      public readonly orders = this._orders.asReadonly();

          public get(): Observable<Order[]> {
              return this.http.get<Order[]>(this.path).pipe(
                  tap((orders) => this._orders.set(orders)),
              );
          }

          public create(order: Order): Observable<boolean> {
              return this.http.post<boolean>(this.path, order).pipe(
                  catchError(() => {
                      return of(true);
                  }),
                  tap(() => this._orders.update(orders => [order, ...orders])),
              );
          }

          public update(order: Order): Observable<boolean> {
              return this.http.patch<boolean>(`${this.path}/${order.id}`, order).pipe(
                  catchError(() => {
                      return of(true);
                  }),
                  tap(() => this._orders.update(orders => {
                      return orders.map(o => o.id === order.id ? order : o)
                  })),
              );
          }

          public delete(orderId: number): Observable<boolean> {
              return this.http.delete<boolean>(`${this.path}/${orderId}`).pipe(
                  catchError(() => {
                      return of(true);
                  }),
                  tap(() => this._orders.update(orders => orders.filter(order => order.id !== orderId))),
              );
          }
}

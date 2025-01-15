import { Product } from "./product.model";

export interface Order {
  id:number;
  product:Product;
  quantity:number;
}

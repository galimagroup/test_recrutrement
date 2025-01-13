export class Product {
    name?: string;
    code?: string;
    category?: string;
    inventory_status?: string;
    price?: number;
    quantity?: number;
    rating?: number;
    description?: string;
    image?: string;
    internal_reference?: string;
    shell_id?: number;
  
    constructor(init?: Partial<Product>) {
      Object.assign(this, init);
    }
  }
  
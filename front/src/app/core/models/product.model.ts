export enum InventoryStatus {
    IN_STOCK = 'INSTOCK',
    LOW_STOCK = 'LOWSTOCK',
    OUT_OF_STOCK = 'OUTOFSTOCK'
  }
  
  export interface Product {
    id: number;
    code: string;
    name: string;
    description: string;
    image: string;
    category: string;
    price: number;
    quantity: number;
    internalReference: string;
    shellId: number;
    inventoryStatus: InventoryStatus;
    rating: number;
    createdAt: Date; 
    updatedAt: Date;  
  }
  

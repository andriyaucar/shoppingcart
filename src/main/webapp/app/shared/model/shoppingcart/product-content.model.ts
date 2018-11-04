import { IProduct } from 'app/shared/model/shoppingcart/product.model';

export interface IProductContent {
  id?: number;
  productContentKey?: string;
  productContentValue?: string;
  product?: IProduct;
}

export class ProductContent implements IProductContent {
  constructor(public id?: number, public productContentKey?: string, public productContentValue?: string, public product?: IProduct) {}
}

import { IProduct } from 'app/shared/model/shoppingcart/product.model';
import { ICart } from 'app/shared/model/shoppingcart/cart.model';

export const enum ProductStatus {
  RESOLVED = 'RESOLVED',
  SOLD = 'SOLD'
}

export interface ICartProductRelation {
  id?: number;
  productStatus?: ProductStatus;
  productCount?: number;
  couponId?: number;
  categoryId?: number;
  product?: IProduct;
  cart?: ICart;
}

export class CartProductRelation implements ICartProductRelation {
  constructor(
    public id?: number,
    public productStatus?: ProductStatus,
    public productCount?: number,
    public couponId?: number,
    public categoryId?: number,
    public product?: IProduct,
    public cart?: ICart
  ) {}
}

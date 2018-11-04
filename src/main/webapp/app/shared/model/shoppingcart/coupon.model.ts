import { ICart } from 'app/shared/model/shoppingcart/cart.model';

export const enum DiscountType {
  RATE = 'RATE',
  AMOUNT = 'AMOUNT'
}

export interface ICoupon {
  id?: number;
  minCartPrice?: number;
  discountType?: DiscountType;
  discountNumber?: number;
  carts?: ICart[];
}

export class Coupon implements ICoupon {
  constructor(
    public id?: number,
    public minCartPrice?: number,
    public discountType?: DiscountType,
    public discountNumber?: number,
    public carts?: ICart[]
  ) {}
}

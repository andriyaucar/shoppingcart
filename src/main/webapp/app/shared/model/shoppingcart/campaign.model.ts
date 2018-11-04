import { ICategory } from 'app/shared/model/shoppingcart/category.model';

export const enum DiscountType {
  RATE = 'RATE',
  AMOUNT = 'AMOUNT'
}

export interface ICampaign {
  id?: number;
  campaignTitle?: string;
  discountNumber?: number;
  discountType?: DiscountType;
  minProductCount?: number;
  categories?: ICategory[];
}

export class Campaign implements ICampaign {
  constructor(
    public id?: number,
    public campaignTitle?: string,
    public discountNumber?: number,
    public discountType?: DiscountType,
    public minProductCount?: number,
    public categories?: ICategory[]
  ) {}
}

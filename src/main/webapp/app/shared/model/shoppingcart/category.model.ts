import { IProduct } from 'app/shared/model/shoppingcart/product.model';
import { ICampaign } from 'app/shared/model/shoppingcart/campaign.model';

export interface ICategory {
  id?: number;
  categoryTitle?: string;
  products?: IProduct[];
  campaigns?: ICampaign[];
}

export class Category implements ICategory {
  constructor(public id?: number, public categoryTitle?: string, public products?: IProduct[], public campaigns?: ICampaign[]) {}
}

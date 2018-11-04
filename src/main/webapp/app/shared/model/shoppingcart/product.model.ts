import { ICategory } from 'app/shared/model/shoppingcart/category.model';
import { IProductContent } from 'app/shared/model/shoppingcart/product-content.model';
import { ICartProductRelation } from 'app/shared/model/shoppingcart/cart-product-relation.model';
import { ICart } from 'app/shared/model/shoppingcart/cart.model';

export interface IProduct {
  id?: number;
  productTitle?: string;
  productPrice?: number;
  totalProductCount?: number;
  availableProductCount?: number;
  productNumber?: string;
  serialNumber?: string;
  category?: ICategory;
  productContents?: IProductContent[];
  cartProductRelations?: ICartProductRelation[];
  carts?: ICart[];
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public productTitle?: string,
    public productPrice?: number,
    public totalProductCount?: number,
    public availableProductCount?: number,
    public productNumber?: string,
    public serialNumber?: string,
    public category?: ICategory,
    public productContents?: IProductContent[],
    public cartProductRelations?: ICartProductRelation[],
    public carts?: ICart[]
  ) {}
}

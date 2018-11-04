import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICartProductRelation } from 'app/shared/model/shoppingcart/cart-product-relation.model';

@Component({
  selector: 'jhi-cart-product-relation-detail',
  templateUrl: './cart-product-relation-detail.component.html'
})
export class CartProductRelationDetailComponent implements OnInit {
  cartProductRelation: ICartProductRelation;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cartProductRelation }) => {
      this.cartProductRelation = cartProductRelation;
    });
  }

  previousState() {
    window.history.back();
  }
}

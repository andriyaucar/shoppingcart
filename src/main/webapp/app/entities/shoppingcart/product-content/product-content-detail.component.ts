import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductContent } from 'app/shared/model/shoppingcart/product-content.model';

@Component({
  selector: 'jhi-product-content-detail',
  templateUrl: './product-content-detail.component.html'
})
export class ProductContentDetailComponent implements OnInit {
  productContent: IProductContent;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productContent }) => {
      this.productContent = productContent;
    });
  }

  previousState() {
    window.history.back();
  }
}

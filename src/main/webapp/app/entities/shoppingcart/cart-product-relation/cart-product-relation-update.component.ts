import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICartProductRelation } from 'app/shared/model/shoppingcart/cart-product-relation.model';
import { CartProductRelationService } from './cart-product-relation.service';
import { IProduct } from 'app/shared/model/shoppingcart/product.model';
import { ProductService } from 'app/entities/shoppingcart/product';
import { ICart } from 'app/shared/model/shoppingcart/cart.model';
import { CartService } from 'app/entities/shoppingcart/cart';

@Component({
  selector: 'jhi-cart-product-relation-update',
  templateUrl: './cart-product-relation-update.component.html'
})
export class CartProductRelationUpdateComponent implements OnInit {
  cartProductRelation: ICartProductRelation;
  isSaving: boolean;

  products: IProduct[];

  carts: ICart[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private cartProductRelationService: CartProductRelationService,
    private productService: ProductService,
    private cartService: CartService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cartProductRelation }) => {
      this.cartProductRelation = cartProductRelation;
    });
    this.productService.query().subscribe(
      (res: HttpResponse<IProduct[]>) => {
        this.products = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.cartService.query().subscribe(
      (res: HttpResponse<ICart[]>) => {
        this.carts = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.cartProductRelation.id !== undefined) {
      this.subscribeToSaveResponse(this.cartProductRelationService.update(this.cartProductRelation));
    } else {
      this.subscribeToSaveResponse(this.cartProductRelationService.create(this.cartProductRelation));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ICartProductRelation>>) {
    result.subscribe((res: HttpResponse<ICartProductRelation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackProductById(index: number, item: IProduct) {
    return item.id;
  }

  trackCartById(index: number, item: ICart) {
    return item.id;
  }
}

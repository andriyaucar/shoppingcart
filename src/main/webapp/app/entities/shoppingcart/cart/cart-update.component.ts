import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICart } from 'app/shared/model/shoppingcart/cart.model';
import { CartService } from './cart.service';
import { IProduct } from 'app/shared/model/shoppingcart/product.model';
import { ProductService } from 'app/entities/shoppingcart/product';
import { ICoupon } from 'app/shared/model/shoppingcart/coupon.model';
import { CouponService } from 'app/entities/shoppingcart/coupon';

@Component({
  selector: 'jhi-cart-update',
  templateUrl: './cart-update.component.html'
})
export class CartUpdateComponent implements OnInit {
  cart: ICart;
  isSaving: boolean;

  products: IProduct[];

  coupons: ICoupon[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private cartService: CartService,
    private productService: ProductService,
    private couponService: CouponService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cart }) => {
      this.cart = cart;
    });
    this.productService.query().subscribe(
      (res: HttpResponse<IProduct[]>) => {
        this.products = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.couponService.query().subscribe(
      (res: HttpResponse<ICoupon[]>) => {
        this.coupons = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.cart.id !== undefined) {
      this.subscribeToSaveResponse(this.cartService.update(this.cart));
    } else {
      this.subscribeToSaveResponse(this.cartService.create(this.cart));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ICart>>) {
    result.subscribe((res: HttpResponse<ICart>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackCouponById(index: number, item: ICoupon) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}

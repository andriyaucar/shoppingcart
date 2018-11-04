import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICoupon } from 'app/shared/model/shoppingcart/coupon.model';
import { CouponService } from './coupon.service';

@Component({
  selector: 'jhi-coupon-update',
  templateUrl: './coupon-update.component.html'
})
export class CouponUpdateComponent implements OnInit {
  coupon: ICoupon;
  isSaving: boolean;

  constructor(private couponService: CouponService, private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ coupon }) => {
      this.coupon = coupon;
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.coupon.id !== undefined) {
      this.subscribeToSaveResponse(this.couponService.update(this.coupon));
    } else {
      this.subscribeToSaveResponse(this.couponService.create(this.coupon));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ICoupon>>) {
    result.subscribe((res: HttpResponse<ICoupon>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  private onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  private onSaveError() {
    this.isSaving = false;
  }
}

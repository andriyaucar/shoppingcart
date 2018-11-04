import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICoupon } from 'app/shared/model/shoppingcart/coupon.model';
import { Principal } from 'app/core';
import { CouponService } from './coupon.service';

@Component({
  selector: 'jhi-coupon',
  templateUrl: './coupon.component.html'
})
export class CouponComponent implements OnInit, OnDestroy {
  coupons: ICoupon[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private couponService: CouponService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.couponService.query().subscribe(
      (res: HttpResponse<ICoupon[]>) => {
        this.coupons = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCoupons();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICoupon) {
    return item.id;
  }

  registerChangeInCoupons() {
    this.eventSubscriber = this.eventManager.subscribe('couponListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

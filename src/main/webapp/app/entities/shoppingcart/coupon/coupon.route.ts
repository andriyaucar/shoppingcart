import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Coupon } from 'app/shared/model/shoppingcart/coupon.model';
import { CouponService } from './coupon.service';
import { CouponComponent } from './coupon.component';
import { CouponDetailComponent } from './coupon-detail.component';
import { CouponUpdateComponent } from './coupon-update.component';
import { CouponDeletePopupComponent } from './coupon-delete-dialog.component';
import { ICoupon } from 'app/shared/model/shoppingcart/coupon.model';

@Injectable({ providedIn: 'root' })
export class CouponResolve implements Resolve<ICoupon> {
  constructor(private service: CouponService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Coupon> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Coupon>) => response.ok),
        map((coupon: HttpResponse<Coupon>) => coupon.body)
      );
    }
    return of(new Coupon());
  }
}

export const couponRoute: Routes = [
  {
    path: 'coupon',
    component: CouponComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Coupons'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'coupon/:id/view',
    component: CouponDetailComponent,
    resolve: {
      coupon: CouponResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Coupons'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'coupon/new',
    component: CouponUpdateComponent,
    resolve: {
      coupon: CouponResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Coupons'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'coupon/:id/edit',
    component: CouponUpdateComponent,
    resolve: {
      coupon: CouponResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Coupons'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const couponPopupRoute: Routes = [
  {
    path: 'coupon/:id/delete',
    component: CouponDeletePopupComponent,
    resolve: {
      coupon: CouponResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Coupons'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

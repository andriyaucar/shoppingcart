import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppingcartSharedModule } from 'app/shared';
import {
  CouponComponent,
  CouponDetailComponent,
  CouponUpdateComponent,
  CouponDeletePopupComponent,
  CouponDeleteDialogComponent,
  couponRoute,
  couponPopupRoute
} from './';

const ENTITY_STATES = [...couponRoute, ...couponPopupRoute];

@NgModule({
  imports: [ShoppingcartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CouponComponent, CouponDetailComponent, CouponUpdateComponent, CouponDeleteDialogComponent, CouponDeletePopupComponent],
  entryComponents: [CouponComponent, CouponUpdateComponent, CouponDeleteDialogComponent, CouponDeletePopupComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShoppingcartCouponModule {}

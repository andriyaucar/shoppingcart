import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ShoppingcartSharedModule } from 'app/shared';
import {
  ProductContentComponent,
  ProductContentDetailComponent,
  ProductContentUpdateComponent,
  ProductContentDeletePopupComponent,
  ProductContentDeleteDialogComponent,
  productContentRoute,
  productContentPopupRoute
} from './';

const ENTITY_STATES = [...productContentRoute, ...productContentPopupRoute];

@NgModule({
  imports: [ShoppingcartSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProductContentComponent,
    ProductContentDetailComponent,
    ProductContentUpdateComponent,
    ProductContentDeleteDialogComponent,
    ProductContentDeletePopupComponent
  ],
  entryComponents: [
    ProductContentComponent,
    ProductContentUpdateComponent,
    ProductContentDeleteDialogComponent,
    ProductContentDeletePopupComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ShoppingcartProductContentModule {}

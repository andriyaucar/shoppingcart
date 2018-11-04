import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CartProductRelation } from 'app/shared/model/shoppingcart/cart-product-relation.model';
import { CartProductRelationService } from './cart-product-relation.service';
import { CartProductRelationComponent } from './cart-product-relation.component';
import { CartProductRelationDetailComponent } from './cart-product-relation-detail.component';
import { CartProductRelationUpdateComponent } from './cart-product-relation-update.component';
import { CartProductRelationDeletePopupComponent } from './cart-product-relation-delete-dialog.component';
import { ICartProductRelation } from 'app/shared/model/shoppingcart/cart-product-relation.model';

@Injectable({ providedIn: 'root' })
export class CartProductRelationResolve implements Resolve<ICartProductRelation> {
  constructor(private service: CartProductRelationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<CartProductRelation> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CartProductRelation>) => response.ok),
        map((cartProductRelation: HttpResponse<CartProductRelation>) => cartProductRelation.body)
      );
    }
    return of(new CartProductRelation());
  }
}

export const cartProductRelationRoute: Routes = [
  {
    path: 'cart-product-relation',
    component: CartProductRelationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CartProductRelations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'cart-product-relation/:id/view',
    component: CartProductRelationDetailComponent,
    resolve: {
      cartProductRelation: CartProductRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CartProductRelations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'cart-product-relation/new',
    component: CartProductRelationUpdateComponent,
    resolve: {
      cartProductRelation: CartProductRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CartProductRelations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'cart-product-relation/:id/edit',
    component: CartProductRelationUpdateComponent,
    resolve: {
      cartProductRelation: CartProductRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CartProductRelations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const cartProductRelationPopupRoute: Routes = [
  {
    path: 'cart-product-relation/:id/delete',
    component: CartProductRelationDeletePopupComponent,
    resolve: {
      cartProductRelation: CartProductRelationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'CartProductRelations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

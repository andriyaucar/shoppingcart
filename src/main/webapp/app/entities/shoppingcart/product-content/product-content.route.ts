import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ProductContent } from 'app/shared/model/shoppingcart/product-content.model';
import { ProductContentService } from './product-content.service';
import { ProductContentComponent } from './product-content.component';
import { ProductContentDetailComponent } from './product-content-detail.component';
import { ProductContentUpdateComponent } from './product-content-update.component';
import { ProductContentDeletePopupComponent } from './product-content-delete-dialog.component';
import { IProductContent } from 'app/shared/model/shoppingcart/product-content.model';

@Injectable({ providedIn: 'root' })
export class ProductContentResolve implements Resolve<IProductContent> {
  constructor(private service: ProductContentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ProductContent> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ProductContent>) => response.ok),
        map((productContent: HttpResponse<ProductContent>) => productContent.body)
      );
    }
    return of(new ProductContent());
  }
}

export const productContentRoute: Routes = [
  {
    path: 'product-content',
    component: ProductContentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ProductContents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'product-content/:id/view',
    component: ProductContentDetailComponent,
    resolve: {
      productContent: ProductContentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ProductContents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'product-content/new',
    component: ProductContentUpdateComponent,
    resolve: {
      productContent: ProductContentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ProductContents'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'product-content/:id/edit',
    component: ProductContentUpdateComponent,
    resolve: {
      productContent: ProductContentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ProductContents'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const productContentPopupRoute: Routes = [
  {
    path: 'product-content/:id/delete',
    component: ProductContentDeletePopupComponent,
    resolve: {
      productContent: ProductContentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ProductContents'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];

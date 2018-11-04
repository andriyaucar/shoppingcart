import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICartProductRelation } from 'app/shared/model/shoppingcart/cart-product-relation.model';
import { Principal } from 'app/core';
import { CartProductRelationService } from './cart-product-relation.service';

@Component({
  selector: 'jhi-cart-product-relation',
  templateUrl: './cart-product-relation.component.html'
})
export class CartProductRelationComponent implements OnInit, OnDestroy {
  cartProductRelations: ICartProductRelation[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private cartProductRelationService: CartProductRelationService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.cartProductRelationService.query().subscribe(
      (res: HttpResponse<ICartProductRelation[]>) => {
        this.cartProductRelations = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCartProductRelations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICartProductRelation) {
    return item.id;
  }

  registerChangeInCartProductRelations() {
    this.eventSubscriber = this.eventManager.subscribe('cartProductRelationListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

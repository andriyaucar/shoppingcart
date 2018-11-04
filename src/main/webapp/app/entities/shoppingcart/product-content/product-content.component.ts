import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProductContent } from 'app/shared/model/shoppingcart/product-content.model';
import { Principal } from 'app/core';
import { ProductContentService } from './product-content.service';

@Component({
  selector: 'jhi-product-content',
  templateUrl: './product-content.component.html'
})
export class ProductContentComponent implements OnInit, OnDestroy {
  productContents: IProductContent[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private productContentService: ProductContentService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.productContentService.query().subscribe(
      (res: HttpResponse<IProductContent[]>) => {
        this.productContents = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInProductContents();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IProductContent) {
    return item.id;
  }

  registerChangeInProductContents() {
    this.eventSubscriber = this.eventManager.subscribe('productContentListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

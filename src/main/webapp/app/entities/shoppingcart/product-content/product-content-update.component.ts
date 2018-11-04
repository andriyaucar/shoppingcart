import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IProductContent } from 'app/shared/model/shoppingcart/product-content.model';
import { ProductContentService } from './product-content.service';
import { IProduct } from 'app/shared/model/shoppingcart/product.model';
import { ProductService } from 'app/entities/shoppingcart/product';

@Component({
  selector: 'jhi-product-content-update',
  templateUrl: './product-content-update.component.html'
})
export class ProductContentUpdateComponent implements OnInit {
  productContent: IProductContent;
  isSaving: boolean;

  products: IProduct[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private productContentService: ProductContentService,
    private productService: ProductService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ productContent }) => {
      this.productContent = productContent;
    });
    this.productService.query().subscribe(
      (res: HttpResponse<IProduct[]>) => {
        this.products = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.productContent.id !== undefined) {
      this.subscribeToSaveResponse(this.productContentService.update(this.productContent));
    } else {
      this.subscribeToSaveResponse(this.productContentService.create(this.productContent));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IProductContent>>) {
    result.subscribe((res: HttpResponse<IProductContent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}

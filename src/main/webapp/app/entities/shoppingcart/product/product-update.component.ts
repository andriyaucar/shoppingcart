import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IProduct } from 'app/shared/model/shoppingcart/product.model';
import { ProductService } from './product.service';
import { ICategory } from 'app/shared/model/shoppingcart/category.model';
import { CategoryService } from 'app/entities/shoppingcart/category';
import { ICart } from 'app/shared/model/shoppingcart/cart.model';
import { CartService } from 'app/entities/shoppingcart/cart';

@Component({
  selector: 'jhi-product-update',
  templateUrl: './product-update.component.html'
})
export class ProductUpdateComponent implements OnInit {
  product: IProduct;
  isSaving: boolean;

  categories: ICategory[];

  carts: ICart[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private productService: ProductService,
    private categoryService: CategoryService,
    private cartService: CartService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ product }) => {
      this.product = product;
    });
    this.categoryService.query().subscribe(
      (res: HttpResponse<ICategory[]>) => {
        this.categories = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.cartService.query().subscribe(
      (res: HttpResponse<ICart[]>) => {
        this.carts = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.product.id !== undefined) {
      this.subscribeToSaveResponse(this.productService.update(this.product));
    } else {
      this.subscribeToSaveResponse(this.productService.create(this.product));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<IProduct>>) {
    result.subscribe((res: HttpResponse<IProduct>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackCategoryById(index: number, item: ICategory) {
    return item.id;
  }

  trackCartById(index: number, item: ICart) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}

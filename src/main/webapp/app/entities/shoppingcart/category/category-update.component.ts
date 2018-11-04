import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICategory } from 'app/shared/model/shoppingcart/category.model';
import { CategoryService } from './category.service';
import { ICampaign } from 'app/shared/model/shoppingcart/campaign.model';
import { CampaignService } from 'app/entities/shoppingcart/campaign';

@Component({
  selector: 'jhi-category-update',
  templateUrl: './category-update.component.html'
})
export class CategoryUpdateComponent implements OnInit {
  category: ICategory;
  isSaving: boolean;

  campaigns: ICampaign[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private categoryService: CategoryService,
    private campaignService: CampaignService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ category }) => {
      this.category = category;
    });
    this.campaignService.query().subscribe(
      (res: HttpResponse<ICampaign[]>) => {
        this.campaigns = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.category.id !== undefined) {
      this.subscribeToSaveResponse(this.categoryService.update(this.category));
    } else {
      this.subscribeToSaveResponse(this.categoryService.create(this.category));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ICategory>>) {
    result.subscribe((res: HttpResponse<ICategory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackCampaignById(index: number, item: ICampaign) {
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

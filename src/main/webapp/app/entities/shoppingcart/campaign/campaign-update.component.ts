import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ICampaign } from 'app/shared/model/shoppingcart/campaign.model';
import { CampaignService } from './campaign.service';
import { ICategory } from 'app/shared/model/shoppingcart/category.model';
import { CategoryService } from 'app/entities/shoppingcart/category';

@Component({
  selector: 'jhi-campaign-update',
  templateUrl: './campaign-update.component.html'
})
export class CampaignUpdateComponent implements OnInit {
  campaign: ICampaign;
  isSaving: boolean;

  categories: ICategory[];

  constructor(
    private jhiAlertService: JhiAlertService,
    private campaignService: CampaignService,
    private categoryService: CategoryService,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ campaign }) => {
      this.campaign = campaign;
    });
    this.categoryService.query().subscribe(
      (res: HttpResponse<ICategory[]>) => {
        this.categories = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    if (this.campaign.id !== undefined) {
      this.subscribeToSaveResponse(this.campaignService.update(this.campaign));
    } else {
      this.subscribeToSaveResponse(this.campaignService.create(this.campaign));
    }
  }

  private subscribeToSaveResponse(result: Observable<HttpResponse<ICampaign>>) {
    result.subscribe((res: HttpResponse<ICampaign>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

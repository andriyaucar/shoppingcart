import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICampaign } from 'app/shared/model/shoppingcart/campaign.model';
import { Principal } from 'app/core';
import { CampaignService } from './campaign.service';

@Component({
  selector: 'jhi-campaign',
  templateUrl: './campaign.component.html'
})
export class CampaignComponent implements OnInit, OnDestroy {
  campaigns: ICampaign[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    private campaignService: CampaignService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private principal: Principal
  ) {}

  loadAll() {
    this.campaignService.query().subscribe(
      (res: HttpResponse<ICampaign[]>) => {
        this.campaigns = res.body;
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
  }

  ngOnInit() {
    this.loadAll();
    this.principal.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCampaigns();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICampaign) {
    return item.id;
  }

  registerChangeInCampaigns() {
    this.eventSubscriber = this.eventManager.subscribe('campaignListModification', response => this.loadAll());
  }

  private onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}

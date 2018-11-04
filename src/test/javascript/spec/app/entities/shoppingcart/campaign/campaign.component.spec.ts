/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ShoppingcartTestModule } from '../../../../test.module';
import { CampaignComponent } from 'app/entities/shoppingcart/campaign/campaign.component';
import { CampaignService } from 'app/entities/shoppingcart/campaign/campaign.service';
import { Campaign } from 'app/shared/model/shoppingcart/campaign.model';

describe('Component Tests', () => {
  describe('Campaign Management Component', () => {
    let comp: CampaignComponent;
    let fixture: ComponentFixture<CampaignComponent>;
    let service: CampaignService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppingcartTestModule],
        declarations: [CampaignComponent],
        providers: []
      })
        .overrideTemplate(CampaignComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CampaignComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CampaignService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Campaign(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.campaigns[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ShoppingcartTestModule } from '../../../../test.module';
import { CouponComponent } from 'app/entities/shoppingcart/coupon/coupon.component';
import { CouponService } from 'app/entities/shoppingcart/coupon/coupon.service';
import { Coupon } from 'app/shared/model/shoppingcart/coupon.model';

describe('Component Tests', () => {
  describe('Coupon Management Component', () => {
    let comp: CouponComponent;
    let fixture: ComponentFixture<CouponComponent>;
    let service: CouponService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppingcartTestModule],
        declarations: [CouponComponent],
        providers: []
      })
        .overrideTemplate(CouponComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CouponComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CouponService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Coupon(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.coupons[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

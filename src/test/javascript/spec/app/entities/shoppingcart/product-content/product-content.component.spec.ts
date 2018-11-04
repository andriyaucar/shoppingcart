/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ShoppingcartTestModule } from '../../../../test.module';
import { ProductContentComponent } from 'app/entities/shoppingcart/product-content/product-content.component';
import { ProductContentService } from 'app/entities/shoppingcart/product-content/product-content.service';
import { ProductContent } from 'app/shared/model/shoppingcart/product-content.model';

describe('Component Tests', () => {
  describe('ProductContent Management Component', () => {
    let comp: ProductContentComponent;
    let fixture: ComponentFixture<ProductContentComponent>;
    let service: ProductContentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppingcartTestModule],
        declarations: [ProductContentComponent],
        providers: []
      })
        .overrideTemplate(ProductContentComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductContentComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductContentService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductContent(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productContents[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

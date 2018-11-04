/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ShoppingcartTestModule } from '../../../../test.module';
import { ProductContentUpdateComponent } from 'app/entities/shoppingcart/product-content/product-content-update.component';
import { ProductContentService } from 'app/entities/shoppingcart/product-content/product-content.service';
import { ProductContent } from 'app/shared/model/shoppingcart/product-content.model';

describe('Component Tests', () => {
  describe('ProductContent Management Update Component', () => {
    let comp: ProductContentUpdateComponent;
    let fixture: ComponentFixture<ProductContentUpdateComponent>;
    let service: ProductContentService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppingcartTestModule],
        declarations: [ProductContentUpdateComponent]
      })
        .overrideTemplate(ProductContentUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductContentUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductContentService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new ProductContent(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.productContent = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.update).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );

      it(
        'Should call create service on save for new entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new ProductContent();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.productContent = entity;
          // WHEN
          comp.save();
          tick(); // simulate async

          // THEN
          expect(service.create).toHaveBeenCalledWith(entity);
          expect(comp.isSaving).toEqual(false);
        })
      );
    });
  });
});

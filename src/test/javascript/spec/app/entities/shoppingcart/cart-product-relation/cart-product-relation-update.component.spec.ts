/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ShoppingcartTestModule } from '../../../../test.module';
import { CartProductRelationUpdateComponent } from 'app/entities/shoppingcart/cart-product-relation/cart-product-relation-update.component';
import { CartProductRelationService } from 'app/entities/shoppingcart/cart-product-relation/cart-product-relation.service';
import { CartProductRelation } from 'app/shared/model/shoppingcart/cart-product-relation.model';

describe('Component Tests', () => {
  describe('CartProductRelation Management Update Component', () => {
    let comp: CartProductRelationUpdateComponent;
    let fixture: ComponentFixture<CartProductRelationUpdateComponent>;
    let service: CartProductRelationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppingcartTestModule],
        declarations: [CartProductRelationUpdateComponent]
      })
        .overrideTemplate(CartProductRelationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CartProductRelationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CartProductRelationService);
    });

    describe('save', () => {
      it(
        'Should call update service on save for existing entity',
        fakeAsync(() => {
          // GIVEN
          const entity = new CartProductRelation(123);
          spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.cartProductRelation = entity;
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
          const entity = new CartProductRelation();
          spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
          comp.cartProductRelation = entity;
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

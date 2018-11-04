/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ShoppingcartTestModule } from '../../../../test.module';
import { CartProductRelationComponent } from 'app/entities/shoppingcart/cart-product-relation/cart-product-relation.component';
import { CartProductRelationService } from 'app/entities/shoppingcart/cart-product-relation/cart-product-relation.service';
import { CartProductRelation } from 'app/shared/model/shoppingcart/cart-product-relation.model';

describe('Component Tests', () => {
  describe('CartProductRelation Management Component', () => {
    let comp: CartProductRelationComponent;
    let fixture: ComponentFixture<CartProductRelationComponent>;
    let service: CartProductRelationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppingcartTestModule],
        declarations: [CartProductRelationComponent],
        providers: []
      })
        .overrideTemplate(CartProductRelationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CartProductRelationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CartProductRelationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CartProductRelation(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.cartProductRelations[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppingcartTestModule } from '../../../../test.module';
import { CartProductRelationDetailComponent } from 'app/entities/shoppingcart/cart-product-relation/cart-product-relation-detail.component';
import { CartProductRelation } from 'app/shared/model/shoppingcart/cart-product-relation.model';

describe('Component Tests', () => {
  describe('CartProductRelation Management Detail Component', () => {
    let comp: CartProductRelationDetailComponent;
    let fixture: ComponentFixture<CartProductRelationDetailComponent>;
    const route = ({ data: of({ cartProductRelation: new CartProductRelation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppingcartTestModule],
        declarations: [CartProductRelationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CartProductRelationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CartProductRelationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cartProductRelation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppingcartTestModule } from '../../../../test.module';
import { CartDetailComponent } from 'app/entities/shoppingcart/cart/cart-detail.component';
import { Cart } from 'app/shared/model/shoppingcart/cart.model';

describe('Component Tests', () => {
  describe('Cart Management Detail Component', () => {
    let comp: CartDetailComponent;
    let fixture: ComponentFixture<CartDetailComponent>;
    const route = ({ data: of({ cart: new Cart(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppingcartTestModule],
        declarations: [CartDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CartDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CartDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.cart).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

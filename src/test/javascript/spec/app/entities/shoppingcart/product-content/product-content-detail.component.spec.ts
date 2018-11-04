/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShoppingcartTestModule } from '../../../../test.module';
import { ProductContentDetailComponent } from 'app/entities/shoppingcart/product-content/product-content-detail.component';
import { ProductContent } from 'app/shared/model/shoppingcart/product-content.model';

describe('Component Tests', () => {
  describe('ProductContent Management Detail Component', () => {
    let comp: ProductContentDetailComponent;
    let fixture: ComponentFixture<ProductContentDetailComponent>;
    const route = ({ data: of({ productContent: new ProductContent(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppingcartTestModule],
        declarations: [ProductContentDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductContentDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductContentDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productContent).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ShoppingcartTestModule } from '../../../../test.module';
import { ProductContentDeleteDialogComponent } from 'app/entities/shoppingcart/product-content/product-content-delete-dialog.component';
import { ProductContentService } from 'app/entities/shoppingcart/product-content/product-content.service';

describe('Component Tests', () => {
  describe('ProductContent Management Delete Component', () => {
    let comp: ProductContentDeleteDialogComponent;
    let fixture: ComponentFixture<ProductContentDeleteDialogComponent>;
    let service: ProductContentService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShoppingcartTestModule],
        declarations: [ProductContentDeleteDialogComponent]
      })
        .overrideTemplate(ProductContentDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductContentDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductContentService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});

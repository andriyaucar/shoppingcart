import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICartProductRelation } from 'app/shared/model/shoppingcart/cart-product-relation.model';
import { CartProductRelationService } from './cart-product-relation.service';

@Component({
  selector: 'jhi-cart-product-relation-delete-dialog',
  templateUrl: './cart-product-relation-delete-dialog.component.html'
})
export class CartProductRelationDeleteDialogComponent {
  cartProductRelation: ICartProductRelation;

  constructor(
    private cartProductRelationService: CartProductRelationService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.cartProductRelationService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'cartProductRelationListModification',
        content: 'Deleted an cartProductRelation'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-cart-product-relation-delete-popup',
  template: ''
})
export class CartProductRelationDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cartProductRelation }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CartProductRelationDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.cartProductRelation = cartProductRelation;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}

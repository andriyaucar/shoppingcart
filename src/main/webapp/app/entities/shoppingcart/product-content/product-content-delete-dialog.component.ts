import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductContent } from 'app/shared/model/shoppingcart/product-content.model';
import { ProductContentService } from './product-content.service';

@Component({
  selector: 'jhi-product-content-delete-dialog',
  templateUrl: './product-content-delete-dialog.component.html'
})
export class ProductContentDeleteDialogComponent {
  productContent: IProductContent;

  constructor(
    private productContentService: ProductContentService,
    public activeModal: NgbActiveModal,
    private eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.productContentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'productContentListModification',
        content: 'Deleted an productContent'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-product-content-delete-popup',
  template: ''
})
export class ProductContentDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ productContent }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProductContentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.productContent = productContent;
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

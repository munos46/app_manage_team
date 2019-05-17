import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChampionnat } from 'app/shared/model/championnat.model';
import { ChampionnatService } from './championnat.service';

@Component({
    selector: 'jhi-championnat-delete-dialog',
    templateUrl: './championnat-delete-dialog.component.html'
})
export class ChampionnatDeleteDialogComponent {
    championnat: IChampionnat;

    constructor(
        private championnatService: ChampionnatService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.championnatService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'championnatListModification',
                content: 'Deleted an championnat'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-championnat-delete-popup',
    template: ''
})
export class ChampionnatDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private route: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.route.data.subscribe(({ championnat }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ChampionnatDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.championnat = championnat.body;
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

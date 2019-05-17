import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISaison } from 'app/shared/model/saison.model';
import { Principal } from 'app/core';
import { SaisonService } from './saison.service';

@Component({
    selector: 'jhi-saison',
    templateUrl: './saison.component.html'
})
export class SaisonComponent implements OnInit, OnDestroy {
    saisons: ISaison[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private saisonService: SaisonService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.saisonService.query().subscribe(
            (res: HttpResponse<ISaison[]>) => {
                this.saisons = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSaisons();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISaison) {
        return item.id;
    }

    registerChangeInSaisons() {
        this.eventSubscriber = this.eventManager.subscribe('saisonListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

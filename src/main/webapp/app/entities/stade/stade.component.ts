import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStade } from 'app/shared/model/stade.model';
import { Principal } from 'app/core';
import { StadeService } from './stade.service';

@Component({
    selector: 'jhi-stade',
    templateUrl: './stade.component.html'
})
export class StadeComponent implements OnInit, OnDestroy {
    stades: IStade[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private stadeService: StadeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.stadeService.query().subscribe(
            (res: HttpResponse<IStade[]>) => {
                this.stades = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInStades();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IStade) {
        return item.id;
    }

    registerChangeInStades() {
        this.eventSubscriber = this.eventManager.subscribe('stadeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

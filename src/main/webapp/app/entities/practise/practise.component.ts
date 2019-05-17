import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPractise } from 'app/shared/model/practise.model';
import { Principal } from 'app/core';
import { PractiseService } from './practise.service';

@Component({
    selector: 'jhi-practise',
    templateUrl: './practise.component.html'
})
export class PractiseComponent implements OnInit, OnDestroy {
    practises: IPractise[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private practiseService: PractiseService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.practiseService.query().subscribe(
            (res: HttpResponse<IPractise[]>) => {
                this.practises = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPractises();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPractise) {
        return item.id;
    }

    registerChangeInPractises() {
        this.eventSubscriber = this.eventManager.subscribe('practiseListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

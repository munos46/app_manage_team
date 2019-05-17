import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IChampionnat } from 'app/shared/model/championnat.model';
import { Principal } from 'app/core';
import { ChampionnatService } from './championnat.service';

@Component({
    selector: 'jhi-championnat',
    templateUrl: './championnat.component.html'
})
export class ChampionnatComponent implements OnInit, OnDestroy {
    championnats: IChampionnat[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private championnatService: ChampionnatService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.championnatService.query().subscribe(
            (res: HttpResponse<IChampionnat[]>) => {
                this.championnats = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInChampionnats();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IChampionnat) {
        return item.id;
    }

    registerChangeInChampionnats() {
        this.eventSubscriber = this.eventManager.subscribe('championnatListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPractise } from 'app/shared/model/practise.model';
import { PractiseService } from './practise.service';
import { IStade } from 'app/shared/model/stade.model';
import { StadeService } from 'app/entities/stade';
import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from 'app/entities/player';
import { IManager } from 'app/shared/model/manager.model';
import { ManagerService } from 'app/entities/manager';

@Component({
    selector: 'jhi-practise-update',
    templateUrl: './practise-update.component.html'
})
export class PractiseUpdateComponent implements OnInit {
    private _practise: IPractise;
    isSaving: boolean;

    stades: IStade[];

    players: IPlayer[];

    managers: IManager[];
    date: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private practiseService: PractiseService,
        private stadeService: StadeService,
        private playerService: PlayerService,
        private managerService: ManagerService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ practise }) => {
            this.practise = practise.body ? practise.body : practise;
        });
        this.stadeService.query({ filter: 'practise-is-null' }).subscribe(
            (res: HttpResponse<IStade[]>) => {
                if (!this.practise.stadeId) {
                    this.stades = res.body;
                } else {
                    this.stadeService.find(this.practise.stadeId).subscribe(
                        (subRes: HttpResponse<IStade>) => {
                            this.stades = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.playerService.query().subscribe(
            (res: HttpResponse<IPlayer[]>) => {
                this.players = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.managerService.query().subscribe(
            (res: HttpResponse<IManager[]>) => {
                this.managers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.practise.date = moment(this.date, DATE_TIME_FORMAT);
        if (this.practise.id !== undefined) {
            this.subscribeToSaveResponse(this.practiseService.update(this.practise));
        } else {
            this.subscribeToSaveResponse(this.practiseService.create(this.practise));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPractise>>) {
        result.subscribe((res: HttpResponse<IPractise>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackStadeById(index: number, item: IStade) {
        return item.id;
    }

    trackPlayerById(index: number, item: IPlayer) {
        return item.id;
    }

    trackManagerById(index: number, item: IManager) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get practise() {
        return this._practise;
    }

    set practise(practise: IPractise) {
        this._practise = practise;
        this.date = moment(practise.date).format();
    }
}

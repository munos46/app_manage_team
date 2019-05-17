import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IEvent } from 'app/shared/model/event.model';
import { EventService } from './event.service';
import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from 'app/entities/team';
import { IStade } from 'app/shared/model/stade.model';
import { StadeService } from 'app/entities/stade';

@Component({
    selector: 'jhi-event-update',
    templateUrl: './event-update.component.html'
})
export class EventUpdateComponent implements OnInit {
    private _event: IEvent;
    isSaving: boolean;

    teams: ITeam[];

    stades: IStade[];
    date: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private eventService: EventService,
        private teamService: TeamService,
        private stadeService: StadeService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ event }) => {
            this.event = event.body ? event.body : event;
        });
        this.teamService.query({ filter: 'event-is-null' }).subscribe(
            (res: HttpResponse<ITeam[]>) => {
                if (!this.event.teamId) {
                    this.teams = res.body;
                } else {
                    this.teamService.find(this.event.teamId).subscribe(
                        (subRes: HttpResponse<ITeam>) => {
                            this.teams = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.stadeService.query({ filter: 'event-is-null' }).subscribe(
            (res: HttpResponse<IStade[]>) => {
                if (!this.event.stadeId) {
                    this.stades = res.body;
                } else {
                    this.stadeService.find(this.event.stadeId).subscribe(
                        (subRes: HttpResponse<IStade>) => {
                            this.stades = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.event.date = moment(this.date, DATE_TIME_FORMAT);
        if (this.event.id !== undefined) {
            this.subscribeToSaveResponse(this.eventService.update(this.event));
        } else {
            this.subscribeToSaveResponse(this.eventService.create(this.event));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEvent>>) {
        result.subscribe((res: HttpResponse<IEvent>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTeamById(index: number, item: ITeam) {
        return item.id;
    }

    trackStadeById(index: number, item: IStade) {
        return item.id;
    }
    get event() {
        return this._event;
    }

    set event(event: IEvent) {
        this._event = event;
        this.date = moment(event.date).format();
    }
}

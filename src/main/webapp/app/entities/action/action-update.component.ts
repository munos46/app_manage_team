import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { IAction } from 'app/shared/model/action.model';
import { ActionService } from './action.service';
import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from 'app/entities/player';

@Component({
    selector: 'jhi-action-update',
    templateUrl: './action-update.component.html'
})
export class ActionUpdateComponent implements OnInit {
    private _action: IAction;
    isSaving: boolean;

    playerones: IPlayer[];

    playertwos: IPlayer[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private actionService: ActionService,
        private playerService: PlayerService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ action }) => {
            this.action = action.body ? action.body : action;
        });
        this.playerService.query({ filter: 'action-is-null' }).subscribe(
            (res: HttpResponse<IPlayer[]>) => {
                if (!this.action.playerOneId) {
                    this.playerones = res.body;
                } else {
                    this.playerService.find(this.action.playerOneId).subscribe(
                        (subRes: HttpResponse<IPlayer>) => {
                            this.playerones = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.playerService.query({ filter: 'action-is-null' }).subscribe(
            (res: HttpResponse<IPlayer[]>) => {
                if (!this.action.playerTwoId) {
                    this.playertwos = res.body;
                } else {
                    this.playerService.find(this.action.playerTwoId).subscribe(
                        (subRes: HttpResponse<IPlayer>) => {
                            this.playertwos = [subRes.body].concat(res.body);
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
        if (this.action.id !== undefined) {
            this.subscribeToSaveResponse(this.actionService.update(this.action));
        } else {
            this.subscribeToSaveResponse(this.actionService.create(this.action));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAction>>) {
        result.subscribe((res: HttpResponse<IAction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackPlayerById(index: number, item: IPlayer) {
        return item.id;
    }
    get action() {
        return this._action;
    }

    set action(action: IAction) {
        this._action = action;
    }
}

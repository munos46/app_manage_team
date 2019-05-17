import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IGame } from 'app/shared/model/game.model';
import { GameService } from './game.service';
import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from 'app/entities/team';
import { IStade } from 'app/shared/model/stade.model';
import { StadeService } from 'app/entities/stade';
import { IManager } from 'app/shared/model/manager.model';
import { ManagerService } from 'app/entities/manager';
import { IPlayer } from 'app/shared/model/player.model';
import { PlayerService } from 'app/entities/player';
import { IAction } from 'app/shared/model/action.model';
import { ActionService } from 'app/entities/action';

@Component({
    selector: 'jhi-game-update',
    templateUrl: './game-update.component.html'
})
export class GameUpdateComponent implements OnInit {
    private _game: IGame;
    isSaving: boolean;

    teams: ITeam[];

    secondteams: ITeam[];

    stades: IStade[];

    managers: IManager[];

    players: IPlayer[];

    actions: IAction[];
    date: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private gameService: GameService,
        private teamService: TeamService,
        private stadeService: StadeService,
        private managerService: ManagerService,
        private playerService: PlayerService,
        private actionService: ActionService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ game }) => {
            this.game = game.body ? game.body : game;
        });
        this.teamService.query({ filter: 'game-is-null' }).subscribe(
            (res: HttpResponse<ITeam[]>) => {
                if (!this.game.teamId) {
                    this.teams = res.body;
                } else {
                    this.teamService.find(this.game.teamId).subscribe(
                        (subRes: HttpResponse<ITeam>) => {
                            this.teams = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.teamService.query({ filter: 'game-is-null' }).subscribe(
            (res: HttpResponse<ITeam[]>) => {
                if (!this.game.secondTeamId) {
                    this.secondteams = res.body;
                } else {
                    this.teamService.find(this.game.secondTeamId).subscribe(
                        (subRes: HttpResponse<ITeam>) => {
                            this.secondteams = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.stadeService.query({ filter: 'game-is-null' }).subscribe(
            (res: HttpResponse<IStade[]>) => {
                if (!this.game.stadeId) {
                    this.stades = res.body;
                } else {
                    this.stadeService.find(this.game.stadeId).subscribe(
                        (subRes: HttpResponse<IStade>) => {
                            this.stades = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.managerService.query().subscribe(
            (res: HttpResponse<IManager[]>) => {
                this.managers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.playerService.query().subscribe(
            (res: HttpResponse<IPlayer[]>) => {
                this.players = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.actionService.query().subscribe(
            (res: HttpResponse<IAction[]>) => {
                this.actions = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.game.date = moment(this.date, DATE_TIME_FORMAT);
        if (this.game.id !== undefined) {
            this.subscribeToSaveResponse(this.gameService.update(this.game));
        } else {
            this.subscribeToSaveResponse(this.gameService.create(this.game));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGame>>) {
        result.subscribe((res: HttpResponse<IGame>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackManagerById(index: number, item: IManager) {
        return item.id;
    }

    trackPlayerById(index: number, item: IPlayer) {
        return item.id;
    }

    trackActionById(index: number, item: IAction) {
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
    get game() {
        return this._game;
    }

    set game(game: IGame) {
        this._game = game;
        this.date = moment(game.date).format();
    }
}

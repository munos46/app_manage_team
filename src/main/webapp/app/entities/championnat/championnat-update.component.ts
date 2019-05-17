import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService } from 'ng-jhipster';

import { IChampionnat } from 'app/shared/model/championnat.model';
import { ChampionnatService } from './championnat.service';
import { ISaison } from 'app/shared/model/saison.model';
import { SaisonService } from 'app/entities/saison';
import { ITeam } from 'app/shared/model/team.model';
import { TeamService } from 'app/entities/team';
import { IGame } from 'app/shared/model/game.model';
import { GameService } from 'app/entities/game';

@Component({
    selector: 'jhi-championnat-update',
    templateUrl: './championnat-update.component.html'
})
export class ChampionnatUpdateComponent implements OnInit {
    private _championnat: IChampionnat;
    isSaving: boolean;

    saisons: ISaison[];

    teams: ITeam[];

    games: IGame[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private championnatService: ChampionnatService,
        private saisonService: SaisonService,
        private teamService: TeamService,
        private gameService: GameService,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ championnat }) => {
            this.championnat = championnat.body ? championnat.body : championnat;
        });
        this.saisonService.query({ filter: 'championnat-is-null' }).subscribe(
            (res: HttpResponse<ISaison[]>) => {
                if (!this.championnat.saisonId) {
                    this.saisons = res.body;
                } else {
                    this.saisonService.find(this.championnat.saisonId).subscribe(
                        (subRes: HttpResponse<ISaison>) => {
                            this.saisons = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.teamService.query().subscribe(
            (res: HttpResponse<ITeam[]>) => {
                this.teams = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.gameService.query().subscribe(
            (res: HttpResponse<IGame[]>) => {
                this.games = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.championnat.id !== undefined) {
            this.subscribeToSaveResponse(this.championnatService.update(this.championnat));
        } else {
            this.subscribeToSaveResponse(this.championnatService.create(this.championnat));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IChampionnat>>) {
        result.subscribe((res: HttpResponse<IChampionnat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSaisonById(index: number, item: ISaison) {
        return item.id;
    }

    trackTeamById(index: number, item: ITeam) {
        return item.id;
    }

    trackGameById(index: number, item: IGame) {
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
    get championnat() {
        return this._championnat;
    }

    set championnat(championnat: IChampionnat) {
        this._championnat = championnat;
    }
}

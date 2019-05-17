import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Game } from 'app/shared/model/game.model';
import { GameService } from './game.service';
import { GameComponent } from './game.component';
import { GameDetailComponent } from './game-detail.component';
import { GameUpdateComponent } from './game-update.component';
import { GameDeletePopupComponent } from './game-delete-dialog.component';

@Injectable()
export class GameResolve implements Resolve<any> {
    constructor(private service: GameService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Game();
    }
}

export const gameRoute: Routes = [
    {
        path: 'game',
        component: GameComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.game.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'game/:id/view',
        component: GameDetailComponent,
        resolve: {
            game: GameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.game.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'game/new',
        component: GameUpdateComponent,
        resolve: {
            game: GameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.game.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'game/:id/edit',
        component: GameUpdateComponent,
        resolve: {
            game: GameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.game.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const gamePopupRoute: Routes = [
    {
        path: 'game/:id/delete',
        component: GameDeletePopupComponent,
        resolve: {
            game: GameResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.game.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Player } from 'app/shared/model/player.model';
import { PlayerService } from './player.service';
import { PlayerComponent } from './player.component';
import { PlayerDetailComponent } from './player-detail.component';
import { PlayerUpdateComponent } from './player-update.component';
import { PlayerDeletePopupComponent } from './player-delete-dialog.component';

@Injectable()
export class PlayerResolve implements Resolve<any> {
    constructor(private service: PlayerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Player();
    }
}

export const playerRoute: Routes = [
    {
        path: 'player',
        component: PlayerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.player.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'player/:id/view',
        component: PlayerDetailComponent,
        resolve: {
            player: PlayerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.player.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'player/new',
        component: PlayerUpdateComponent,
        resolve: {
            player: PlayerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.player.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'player/:id/edit',
        component: PlayerUpdateComponent,
        resolve: {
            player: PlayerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.player.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const playerPopupRoute: Routes = [
    {
        path: 'player/:id/delete',
        component: PlayerDeletePopupComponent,
        resolve: {
            player: PlayerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.player.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Championnat } from 'app/shared/model/championnat.model';
import { ChampionnatService } from './championnat.service';
import { ChampionnatComponent } from './championnat.component';
import { ChampionnatDetailComponent } from './championnat-detail.component';
import { ChampionnatUpdateComponent } from './championnat-update.component';
import { ChampionnatDeletePopupComponent } from './championnat-delete-dialog.component';

@Injectable()
export class ChampionnatResolve implements Resolve<any> {
    constructor(private service: ChampionnatService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Championnat();
    }
}

export const championnatRoute: Routes = [
    {
        path: 'championnat',
        component: ChampionnatComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.championnat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'championnat/:id/view',
        component: ChampionnatDetailComponent,
        resolve: {
            championnat: ChampionnatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.championnat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'championnat/new',
        component: ChampionnatUpdateComponent,
        resolve: {
            championnat: ChampionnatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.championnat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'championnat/:id/edit',
        component: ChampionnatUpdateComponent,
        resolve: {
            championnat: ChampionnatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.championnat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const championnatPopupRoute: Routes = [
    {
        path: 'championnat/:id/delete',
        component: ChampionnatDeletePopupComponent,
        resolve: {
            championnat: ChampionnatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.championnat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

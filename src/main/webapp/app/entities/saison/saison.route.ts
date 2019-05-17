import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Saison } from 'app/shared/model/saison.model';
import { SaisonService } from './saison.service';
import { SaisonComponent } from './saison.component';
import { SaisonDetailComponent } from './saison-detail.component';
import { SaisonUpdateComponent } from './saison-update.component';
import { SaisonDeletePopupComponent } from './saison-delete-dialog.component';

@Injectable()
export class SaisonResolve implements Resolve<any> {
    constructor(private service: SaisonService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Saison();
    }
}

export const saisonRoute: Routes = [
    {
        path: 'saison',
        component: SaisonComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.saison.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'saison/:id/view',
        component: SaisonDetailComponent,
        resolve: {
            saison: SaisonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.saison.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'saison/new',
        component: SaisonUpdateComponent,
        resolve: {
            saison: SaisonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.saison.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'saison/:id/edit',
        component: SaisonUpdateComponent,
        resolve: {
            saison: SaisonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.saison.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const saisonPopupRoute: Routes = [
    {
        path: 'saison/:id/delete',
        component: SaisonDeletePopupComponent,
        resolve: {
            saison: SaisonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.saison.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

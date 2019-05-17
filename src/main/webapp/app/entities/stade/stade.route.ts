import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Stade } from 'app/shared/model/stade.model';
import { StadeService } from './stade.service';
import { StadeComponent } from './stade.component';
import { StadeDetailComponent } from './stade-detail.component';
import { StadeUpdateComponent } from './stade-update.component';
import { StadeDeletePopupComponent } from './stade-delete-dialog.component';

@Injectable()
export class StadeResolve implements Resolve<any> {
    constructor(private service: StadeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Stade();
    }
}

export const stadeRoute: Routes = [
    {
        path: 'stade',
        component: StadeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.stade.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stade/:id/view',
        component: StadeDetailComponent,
        resolve: {
            stade: StadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.stade.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stade/new',
        component: StadeUpdateComponent,
        resolve: {
            stade: StadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.stade.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'stade/:id/edit',
        component: StadeUpdateComponent,
        resolve: {
            stade: StadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.stade.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stadePopupRoute: Routes = [
    {
        path: 'stade/:id/delete',
        component: StadeDeletePopupComponent,
        resolve: {
            stade: StadeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.stade.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

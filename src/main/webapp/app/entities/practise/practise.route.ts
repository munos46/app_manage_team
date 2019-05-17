import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Practise } from 'app/shared/model/practise.model';
import { PractiseService } from './practise.service';
import { PractiseComponent } from './practise.component';
import { PractiseDetailComponent } from './practise-detail.component';
import { PractiseUpdateComponent } from './practise-update.component';
import { PractiseDeletePopupComponent } from './practise-delete-dialog.component';

@Injectable()
export class PractiseResolve implements Resolve<any> {
    constructor(private service: PractiseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Practise();
    }
}

export const practiseRoute: Routes = [
    {
        path: 'practise',
        component: PractiseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.practise.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'practise/:id/view',
        component: PractiseDetailComponent,
        resolve: {
            practise: PractiseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.practise.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'practise/new',
        component: PractiseUpdateComponent,
        resolve: {
            practise: PractiseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.practise.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'practise/:id/edit',
        component: PractiseUpdateComponent,
        resolve: {
            practise: PractiseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.practise.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const practisePopupRoute: Routes = [
    {
        path: 'practise/:id/delete',
        component: PractiseDeletePopupComponent,
        resolve: {
            practise: PractiseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.practise.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Action } from 'app/shared/model/action.model';
import { ActionService } from './action.service';
import { ActionComponent } from './action.component';
import { ActionDetailComponent } from './action-detail.component';
import { ActionUpdateComponent } from './action-update.component';
import { ActionDeletePopupComponent } from './action-delete-dialog.component';

@Injectable()
export class ActionResolve implements Resolve<any> {
    constructor(private service: ActionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Action();
    }
}

export const actionRoute: Routes = [
    {
        path: 'action',
        component: ActionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.action.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'action/:id/view',
        component: ActionDetailComponent,
        resolve: {
            action: ActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.action.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'action/new',
        component: ActionUpdateComponent,
        resolve: {
            action: ActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.action.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'action/:id/edit',
        component: ActionUpdateComponent,
        resolve: {
            action: ActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.action.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const actionPopupRoute: Routes = [
    {
        path: 'action/:id/delete',
        component: ActionDeletePopupComponent,
        resolve: {
            action: ActionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.action.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

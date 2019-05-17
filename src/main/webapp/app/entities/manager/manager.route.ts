import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Manager } from 'app/shared/model/manager.model';
import { ManagerService } from './manager.service';
import { ManagerComponent } from './manager.component';
import { ManagerDetailComponent } from './manager-detail.component';
import { ManagerUpdateComponent } from './manager-update.component';
import { ManagerDeletePopupComponent } from './manager-delete-dialog.component';

@Injectable()
export class ManagerResolve implements Resolve<any> {
    constructor(private service: ManagerService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Manager();
    }
}

export const managerRoute: Routes = [
    {
        path: 'manager',
        component: ManagerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.manager.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'manager/:id/view',
        component: ManagerDetailComponent,
        resolve: {
            manager: ManagerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.manager.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'manager/new',
        component: ManagerUpdateComponent,
        resolve: {
            manager: ManagerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.manager.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'manager/:id/edit',
        component: ManagerUpdateComponent,
        resolve: {
            manager: ManagerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.manager.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const managerPopupRoute: Routes = [
    {
        path: 'manager/:id/delete',
        component: ManagerDeletePopupComponent,
        resolve: {
            manager: ManagerResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.manager.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

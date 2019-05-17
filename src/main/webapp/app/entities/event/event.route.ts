import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { Event } from 'app/shared/model/event.model';
import { EventService } from './event.service';
import { EventComponent } from './event.component';
import { EventDetailComponent } from './event-detail.component';
import { EventUpdateComponent } from './event-update.component';
import { EventDeletePopupComponent } from './event-delete-dialog.component';

@Injectable()
export class EventResolve implements Resolve<any> {
    constructor(private service: EventService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id);
        }
        return new Event();
    }
}

export const eventRoute: Routes = [
    {
        path: 'event',
        component: EventComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.event.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'event/:id/view',
        component: EventDetailComponent,
        resolve: {
            event: EventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.event.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'event/new',
        component: EventUpdateComponent,
        resolve: {
            event: EventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.event.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'event/:id/edit',
        component: EventUpdateComponent,
        resolve: {
            event: EventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.event.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const eventPopupRoute: Routes = [
    {
        path: 'event/:id/delete',
        component: EventDeletePopupComponent,
        resolve: {
            event: EventResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'manageTeamApp.event.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

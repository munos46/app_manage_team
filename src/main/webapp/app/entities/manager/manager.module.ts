import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManageTeamSharedModule } from 'app/shared';
import { ManageTeamAdminModule } from 'app/admin/admin.module';
import {
    ManagerService,
    ManagerComponent,
    ManagerDetailComponent,
    ManagerUpdateComponent,
    ManagerDeletePopupComponent,
    ManagerDeleteDialogComponent,
    managerRoute,
    managerPopupRoute,
    ManagerResolve
} from './';

const ENTITY_STATES = [...managerRoute, ...managerPopupRoute];

@NgModule({
    imports: [ManageTeamSharedModule, ManageTeamAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ManagerComponent,
        ManagerDetailComponent,
        ManagerUpdateComponent,
        ManagerDeleteDialogComponent,
        ManagerDeletePopupComponent
    ],
    entryComponents: [ManagerComponent, ManagerUpdateComponent, ManagerDeleteDialogComponent, ManagerDeletePopupComponent],
    providers: [ManagerService, ManagerResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManageTeamManagerModule {}

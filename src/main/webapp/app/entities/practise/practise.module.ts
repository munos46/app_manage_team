import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManageTeamSharedModule } from 'app/shared';
import {
    PractiseService,
    PractiseComponent,
    PractiseDetailComponent,
    PractiseUpdateComponent,
    PractiseDeletePopupComponent,
    PractiseDeleteDialogComponent,
    practiseRoute,
    practisePopupRoute,
    PractiseResolve
} from './';

const ENTITY_STATES = [...practiseRoute, ...practisePopupRoute];

@NgModule({
    imports: [ManageTeamSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PractiseComponent,
        PractiseDetailComponent,
        PractiseUpdateComponent,
        PractiseDeleteDialogComponent,
        PractiseDeletePopupComponent
    ],
    entryComponents: [PractiseComponent, PractiseUpdateComponent, PractiseDeleteDialogComponent, PractiseDeletePopupComponent],
    providers: [PractiseService, PractiseResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManageTeamPractiseModule {}

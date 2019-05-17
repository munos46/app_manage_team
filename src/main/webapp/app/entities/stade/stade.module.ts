import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManageTeamSharedModule } from 'app/shared';
import {
    StadeService,
    StadeComponent,
    StadeDetailComponent,
    StadeUpdateComponent,
    StadeDeletePopupComponent,
    StadeDeleteDialogComponent,
    stadeRoute,
    stadePopupRoute,
    StadeResolve
} from './';

const ENTITY_STATES = [...stadeRoute, ...stadePopupRoute];

@NgModule({
    imports: [ManageTeamSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [StadeComponent, StadeDetailComponent, StadeUpdateComponent, StadeDeleteDialogComponent, StadeDeletePopupComponent],
    entryComponents: [StadeComponent, StadeUpdateComponent, StadeDeleteDialogComponent, StadeDeletePopupComponent],
    providers: [StadeService, StadeResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManageTeamStadeModule {}

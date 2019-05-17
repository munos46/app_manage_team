import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManageTeamSharedModule } from 'app/shared';
import {
    SaisonService,
    SaisonComponent,
    SaisonDetailComponent,
    SaisonUpdateComponent,
    SaisonDeletePopupComponent,
    SaisonDeleteDialogComponent,
    saisonRoute,
    saisonPopupRoute,
    SaisonResolve
} from './';

const ENTITY_STATES = [...saisonRoute, ...saisonPopupRoute];

@NgModule({
    imports: [ManageTeamSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SaisonComponent, SaisonDetailComponent, SaisonUpdateComponent, SaisonDeleteDialogComponent, SaisonDeletePopupComponent],
    entryComponents: [SaisonComponent, SaisonUpdateComponent, SaisonDeleteDialogComponent, SaisonDeletePopupComponent],
    providers: [SaisonService, SaisonResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManageTeamSaisonModule {}

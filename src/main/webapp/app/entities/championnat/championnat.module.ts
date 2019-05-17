import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManageTeamSharedModule } from 'app/shared';
import {
    ChampionnatService,
    ChampionnatComponent,
    ChampionnatDetailComponent,
    ChampionnatUpdateComponent,
    ChampionnatDeletePopupComponent,
    ChampionnatDeleteDialogComponent,
    championnatRoute,
    championnatPopupRoute,
    ChampionnatResolve
} from './';

const ENTITY_STATES = [...championnatRoute, ...championnatPopupRoute];

@NgModule({
    imports: [ManageTeamSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ChampionnatComponent,
        ChampionnatDetailComponent,
        ChampionnatUpdateComponent,
        ChampionnatDeleteDialogComponent,
        ChampionnatDeletePopupComponent
    ],
    entryComponents: [ChampionnatComponent, ChampionnatUpdateComponent, ChampionnatDeleteDialogComponent, ChampionnatDeletePopupComponent],
    providers: [ChampionnatService, ChampionnatResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManageTeamChampionnatModule {}

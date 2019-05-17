import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ManageTeamSharedModule } from 'app/shared';
import {
    GameService,
    GameComponent,
    GameDetailComponent,
    GameUpdateComponent,
    GameDeletePopupComponent,
    GameDeleteDialogComponent,
    gameRoute,
    gamePopupRoute,
    GameResolve
} from './';

const ENTITY_STATES = [...gameRoute, ...gamePopupRoute];

@NgModule({
    imports: [ManageTeamSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [GameComponent, GameDetailComponent, GameUpdateComponent, GameDeleteDialogComponent, GameDeletePopupComponent],
    entryComponents: [GameComponent, GameUpdateComponent, GameDeleteDialogComponent, GameDeletePopupComponent],
    providers: [GameService, GameResolve],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManageTeamGameModule {}

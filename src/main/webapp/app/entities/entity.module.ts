import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ManageTeamPlayerModule } from './player/player.module';
import { ManageTeamManagerModule } from './manager/manager.module';
import { ManageTeamTeamModule } from './team/team.module';
import { ManageTeamStadeModule } from './stade/stade.module';
import { ManageTeamGameModule } from './game/game.module';
import { ManageTeamEventModule } from './event/event.module';
import { ManageTeamPractiseModule } from './practise/practise.module';
import { ManageTeamActionModule } from './action/action.module';
import { ManageTeamSaisonModule } from './saison/saison.module';
import { ManageTeamChampionnatModule } from './championnat/championnat.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        ManageTeamPlayerModule,
        ManageTeamManagerModule,
        ManageTeamTeamModule,
        ManageTeamStadeModule,
        ManageTeamGameModule,
        ManageTeamEventModule,
        ManageTeamPractiseModule,
        ManageTeamActionModule,
        ManageTeamSaisonModule,
        ManageTeamChampionnatModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ManageTeamEntityModule {}

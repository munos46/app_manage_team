import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChampionnat } from 'app/shared/model/championnat.model';

@Component({
    selector: 'jhi-championnat-detail',
    templateUrl: './championnat-detail.component.html'
})
export class ChampionnatDetailComponent implements OnInit {
    championnat: IChampionnat;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ championnat }) => {
            this.championnat = championnat.body ? championnat.body : championnat;
        });
    }

    previousState() {
        window.history.back();
    }
}

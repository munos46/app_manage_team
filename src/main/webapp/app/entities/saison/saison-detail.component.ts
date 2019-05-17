import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISaison } from 'app/shared/model/saison.model';

@Component({
    selector: 'jhi-saison-detail',
    templateUrl: './saison-detail.component.html'
})
export class SaisonDetailComponent implements OnInit {
    saison: ISaison;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ saison }) => {
            this.saison = saison.body ? saison.body : saison;
        });
    }

    previousState() {
        window.history.back();
    }
}

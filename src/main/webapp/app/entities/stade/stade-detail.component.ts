import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStade } from 'app/shared/model/stade.model';

@Component({
    selector: 'jhi-stade-detail',
    templateUrl: './stade-detail.component.html'
})
export class StadeDetailComponent implements OnInit {
    stade: IStade;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ stade }) => {
            this.stade = stade.body ? stade.body : stade;
        });
    }

    previousState() {
        window.history.back();
    }
}

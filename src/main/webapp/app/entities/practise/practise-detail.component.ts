import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPractise } from 'app/shared/model/practise.model';

@Component({
    selector: 'jhi-practise-detail',
    templateUrl: './practise-detail.component.html'
})
export class PractiseDetailComponent implements OnInit {
    practise: IPractise;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ practise }) => {
            this.practise = practise.body ? practise.body : practise;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAction } from 'app/shared/model/action.model';

@Component({
    selector: 'jhi-action-detail',
    templateUrl: './action-detail.component.html'
})
export class ActionDetailComponent implements OnInit {
    action: IAction;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ action }) => {
            this.action = action.body ? action.body : action;
        });
    }

    previousState() {
        window.history.back();
    }
}

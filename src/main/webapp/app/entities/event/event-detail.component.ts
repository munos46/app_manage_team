import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEvent } from 'app/shared/model/event.model';

@Component({
    selector: 'jhi-event-detail',
    templateUrl: './event-detail.component.html'
})
export class EventDetailComponent implements OnInit {
    event: IEvent;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ event }) => {
            this.event = event.body ? event.body : event;
        });
    }

    previousState() {
        window.history.back();
    }
}

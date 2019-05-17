import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IManager } from 'app/shared/model/manager.model';

@Component({
    selector: 'jhi-manager-detail',
    templateUrl: './manager-detail.component.html'
})
export class ManagerDetailComponent implements OnInit {
    manager: IManager;

    constructor(private dataUtils: JhiDataUtils, private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ manager }) => {
            this.manager = manager.body ? manager.body : manager;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}

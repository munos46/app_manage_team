import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IManager } from 'app/shared/model/manager.model';
import { ManagerService } from './manager.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-manager-update',
    templateUrl: './manager-update.component.html'
})
export class ManagerUpdateComponent implements OnInit {
    private _manager: IManager;
    isSaving: boolean;

    users: IUser[];
    hireDateDp: any;
    anneeArriveeDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private managerService: ManagerService,
        private userService: UserService,
        private elementRef: ElementRef,
        private route: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ manager }) => {
            this.manager = manager.body ? manager.body : manager;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.manager, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.manager.id !== undefined) {
            this.subscribeToSaveResponse(this.managerService.update(this.manager));
        } else {
            this.subscribeToSaveResponse(this.managerService.create(this.manager));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IManager>>) {
        result.subscribe((res: HttpResponse<IManager>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get manager() {
        return this._manager;
    }

    set manager(manager: IManager) {
        this._manager = manager;
    }
}

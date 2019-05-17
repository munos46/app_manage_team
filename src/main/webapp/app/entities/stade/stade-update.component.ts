import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { IStade } from 'app/shared/model/stade.model';
import { StadeService } from './stade.service';

@Component({
    selector: 'jhi-stade-update',
    templateUrl: './stade-update.component.html'
})
export class StadeUpdateComponent implements OnInit {
    private _stade: IStade;
    isSaving: boolean;

    constructor(private stadeService: StadeService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ stade }) => {
            this.stade = stade.body ? stade.body : stade;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.stade.id !== undefined) {
            this.subscribeToSaveResponse(this.stadeService.update(this.stade));
        } else {
            this.subscribeToSaveResponse(this.stadeService.create(this.stade));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IStade>>) {
        result.subscribe((res: HttpResponse<IStade>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get stade() {
        return this._stade;
    }

    set stade(stade: IStade) {
        this._stade = stade;
    }
}

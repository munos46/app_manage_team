import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ISaison } from 'app/shared/model/saison.model';
import { SaisonService } from './saison.service';

@Component({
    selector: 'jhi-saison-update',
    templateUrl: './saison-update.component.html'
})
export class SaisonUpdateComponent implements OnInit {
    private _saison: ISaison;
    isSaving: boolean;
    dateDebutDp: any;
    dateFinDp: any;

    constructor(private saisonService: SaisonService, private route: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.route.data.subscribe(({ saison }) => {
            this.saison = saison.body ? saison.body : saison;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.saison.id !== undefined) {
            this.subscribeToSaveResponse(this.saisonService.update(this.saison));
        } else {
            this.subscribeToSaveResponse(this.saisonService.create(this.saison));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISaison>>) {
        result.subscribe((res: HttpResponse<ISaison>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get saison() {
        return this._saison;
    }

    set saison(saison: ISaison) {
        this._saison = saison;
    }
}

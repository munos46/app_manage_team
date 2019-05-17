import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISaison } from 'app/shared/model/saison.model';

type EntityResponseType = HttpResponse<ISaison>;
type EntityArrayResponseType = HttpResponse<ISaison[]>;

@Injectable()
export class SaisonService {
    private resourceUrl = SERVER_API_URL + 'api/saisons';

    constructor(private http: HttpClient) {}

    create(saison: ISaison): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(saison);
        return this.http
            .post<ISaison>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(saison: ISaison): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(saison);
        return this.http
            .put<ISaison>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISaison>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISaison[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(saison: ISaison): ISaison {
        const copy: ISaison = Object.assign({}, saison, {
            dateDebut: saison.dateDebut != null && saison.dateDebut.isValid() ? saison.dateDebut.toJSON() : null,
            dateFin: saison.dateFin != null && saison.dateFin.isValid() ? saison.dateFin.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateDebut = res.body.dateDebut != null ? moment(res.body.dateDebut) : null;
        res.body.dateFin = res.body.dateFin != null ? moment(res.body.dateFin) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((saison: ISaison) => {
            saison.dateDebut = saison.dateDebut != null ? moment(saison.dateDebut) : null;
            saison.dateFin = saison.dateFin != null ? moment(saison.dateFin) : null;
        });
        return res;
    }
}

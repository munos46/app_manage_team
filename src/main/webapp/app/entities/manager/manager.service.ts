import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IManager } from 'app/shared/model/manager.model';

type EntityResponseType = HttpResponse<IManager>;
type EntityArrayResponseType = HttpResponse<IManager[]>;

@Injectable()
export class ManagerService {
    private resourceUrl = SERVER_API_URL + 'api/managers';

    constructor(private http: HttpClient) {}

    create(manager: IManager): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(manager);
        return this.http
            .post<IManager>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(manager: IManager): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(manager);
        return this.http
            .put<IManager>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IManager>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IManager[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(manager: IManager): IManager {
        const copy: IManager = Object.assign({}, manager, {
            hireDate: manager.hireDate != null && manager.hireDate.isValid() ? manager.hireDate.toJSON() : null,
            anneeArrivee: manager.anneeArrivee != null && manager.anneeArrivee.isValid() ? manager.anneeArrivee.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.hireDate = res.body.hireDate != null ? moment(res.body.hireDate) : null;
        res.body.anneeArrivee = res.body.anneeArrivee != null ? moment(res.body.anneeArrivee) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((manager: IManager) => {
            manager.hireDate = manager.hireDate != null ? moment(manager.hireDate) : null;
            manager.anneeArrivee = manager.anneeArrivee != null ? moment(manager.anneeArrivee) : null;
        });
        return res;
    }
}

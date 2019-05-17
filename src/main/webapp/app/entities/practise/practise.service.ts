import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPractise } from 'app/shared/model/practise.model';

type EntityResponseType = HttpResponse<IPractise>;
type EntityArrayResponseType = HttpResponse<IPractise[]>;

@Injectable()
export class PractiseService {
    private resourceUrl = SERVER_API_URL + 'api/practises';

    constructor(private http: HttpClient) {}

    create(practise: IPractise): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(practise);
        return this.http
            .post<IPractise>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(practise: IPractise): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(practise);
        return this.http
            .put<IPractise>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPractise>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPractise[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(practise: IPractise): IPractise {
        const copy: IPractise = Object.assign({}, practise, {
            date: practise.date != null && practise.date.isValid() ? practise.date.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((practise: IPractise) => {
            practise.date = practise.date != null ? moment(practise.date) : null;
        });
        return res;
    }
}

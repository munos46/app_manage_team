import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStade } from 'app/shared/model/stade.model';

type EntityResponseType = HttpResponse<IStade>;
type EntityArrayResponseType = HttpResponse<IStade[]>;

@Injectable()
export class StadeService {
    private resourceUrl = SERVER_API_URL + 'api/stades';

    constructor(private http: HttpClient) {}

    create(stade: IStade): Observable<EntityResponseType> {
        return this.http.post<IStade>(this.resourceUrl, stade, { observe: 'response' });
    }

    update(stade: IStade): Observable<EntityResponseType> {
        return this.http.put<IStade>(this.resourceUrl, stade, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStade>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStade[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

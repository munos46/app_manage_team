import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChampionnat } from 'app/shared/model/championnat.model';

type EntityResponseType = HttpResponse<IChampionnat>;
type EntityArrayResponseType = HttpResponse<IChampionnat[]>;

@Injectable()
export class ChampionnatService {
    private resourceUrl = SERVER_API_URL + 'api/championnats';

    constructor(private http: HttpClient) {}

    create(championnat: IChampionnat): Observable<EntityResponseType> {
        return this.http.post<IChampionnat>(this.resourceUrl, championnat, { observe: 'response' });
    }

    update(championnat: IChampionnat): Observable<EntityResponseType> {
        return this.http.put<IChampionnat>(this.resourceUrl, championnat, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IChampionnat>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IChampionnat[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

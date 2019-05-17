import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IPlayer } from 'app/shared/model/player.model';

type EntityResponseType = HttpResponse<IPlayer>;
type EntityArrayResponseType = HttpResponse<IPlayer[]>;

@Injectable()
export class PlayerService {
    private resourceUrl = SERVER_API_URL + 'api/players';

    constructor(private http: HttpClient) {}

    create(player: IPlayer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(player);
        return this.http
            .post<IPlayer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    update(player: IPlayer): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(player);
        return this.http
            .put<IPlayer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IPlayer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertDateFromServer(res));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IPlayer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(player: IPlayer): IPlayer {
        const copy: IPlayer = Object.assign({}, player, {
            hireDate: player.hireDate != null && player.hireDate.isValid() ? player.hireDate.toJSON() : null,
            anneeArrivee: player.anneeArrivee != null && player.anneeArrivee.isValid() ? player.anneeArrivee.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.hireDate = res.body.hireDate != null ? moment(res.body.hireDate) : null;
        res.body.anneeArrivee = res.body.anneeArrivee != null ? moment(res.body.anneeArrivee) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((player: IPlayer) => {
            player.hireDate = player.hireDate != null ? moment(player.hireDate) : null;
            player.anneeArrivee = player.anneeArrivee != null ? moment(player.anneeArrivee) : null;
        });
        return res;
    }
}

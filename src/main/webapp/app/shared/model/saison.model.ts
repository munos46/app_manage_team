import { Moment } from 'moment';

export interface ISaison {
    id?: number;
    nom?: string;
    dateDebut?: Moment;
    dateFin?: Moment;
}

export class Saison implements ISaison {
    constructor(public id?: number, public nom?: string, public dateDebut?: Moment, public dateFin?: Moment) {}
}

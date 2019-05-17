import { Moment } from 'moment';

export const enum TypeEvenement {
    AMICAL = 'AMICAL',
    SOIREE = 'SOIREE',
    TOURNOIS = 'TOURNOIS',
    AUTRES = 'AUTRES'
}

export interface IEvent {
    id?: number;
    nom?: string;
    date?: Moment;
    type?: TypeEvenement;
    teamId?: number;
    stadeId?: number;
}

export class Event implements IEvent {
    constructor(
        public id?: number,
        public nom?: string,
        public date?: Moment,
        public type?: TypeEvenement,
        public teamId?: number,
        public stadeId?: number
    ) {}
}

import { Moment } from 'moment';

export interface IPlayer {
    id?: number;
    firstName?: string;
    lastName?: string;
    email?: string;
    phoneNumber?: string;
    hireDate?: Moment;
    anneeArrivee?: Moment;
    poids?: number;
    taille?: number;
    numMaillot?: number;
    numLicence?: string;
    imgProfileContentType?: string;
    imgProfile?: any;
    playerId?: number;
}

export class Player implements IPlayer {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phoneNumber?: string,
        public hireDate?: Moment,
        public anneeArrivee?: Moment,
        public poids?: number,
        public taille?: number,
        public numMaillot?: number,
        public numLicence?: string,
        public imgProfileContentType?: string,
        public imgProfile?: any,
        public playerId?: number
    ) {}
}

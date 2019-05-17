import { Moment } from 'moment';

export interface IManager {
    id?: number;
    firstName?: string;
    lastName?: string;
    email?: string;
    phoneNumber?: string;
    hireDate?: Moment;
    anneeArrivee?: Moment;
    imgProfileContentType?: string;
    imgProfile?: any;
    managerId?: number;
}

export class Manager implements IManager {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phoneNumber?: string,
        public hireDate?: Moment,
        public anneeArrivee?: Moment,
        public imgProfileContentType?: string,
        public imgProfile?: any,
        public managerId?: number
    ) {}
}

import { Moment } from 'moment';
import { IPlayer } from './player.model';
import { IManager } from './manager.model';

export interface IPractise {
    id?: number;
    date?: Moment;
    stadeId?: number;
    players?: IPlayer[];
    manages?: IManager[];
}

export class Practise implements IPractise {
    constructor(
        public id?: number,
        public date?: Moment,
        public stadeId?: number,
        public players?: IPlayer[],
        public manages?: IManager[]
    ) {}
}

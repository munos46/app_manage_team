import { Moment } from 'moment';
import { IManager } from './manager.model';
import { IPlayer } from './player.model';
import { IAction } from './action.model';

export interface IGame {
    id?: number;
    date?: Moment;
    home?: boolean;
    myGoal?: number;
    herGoal?: number;
    teamId?: number;
    secondTeamId?: number;
    stadeId?: number;
    manages?: IManager[];
    players?: IPlayer[];
    actions?: IAction[];
}

export class Game implements IGame {
    constructor(
        public id?: number,
        public date?: Moment,
        public home?: boolean,
        public myGoal?: number,
        public herGoal?: number,
        public teamId?: number,
        public secondTeamId?: number,
        public stadeId?: number,
        public manages?: IManager[],
        public players?: IPlayer[],
        public actions?: IAction[]
    ) {
        this.home = false;
    }
}

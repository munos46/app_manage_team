import { IManager } from './manager.model';
import { IPlayer } from './player.model';

export interface ITeam {
    id?: number;
    nom?: string;
    adversaire?: boolean;
    logoContentType?: string;
    logo?: any;
    managers?: IManager[];
    players?: IPlayer[];
}

export class Team implements ITeam {
    constructor(
        public id?: number,
        public nom?: string,
        public adversaire?: boolean,
        public logoContentType?: string,
        public logo?: any,
        public managers?: IManager[],
        public players?: IPlayer[]
    ) {
        this.adversaire = false;
    }
}

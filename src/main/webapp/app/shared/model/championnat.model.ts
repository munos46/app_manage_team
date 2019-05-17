import { ITeam } from './team.model';
import { IGame } from './game.model';

export interface IChampionnat {
    id?: number;
    nom?: string;
    pointVictoire?: number;
    pointNull?: number;
    pointDefaite?: number;
    pointForfait?: number;
    saisonId?: number;
    adversaires?: ITeam[];
    journees?: IGame[];
}

export class Championnat implements IChampionnat {
    constructor(
        public id?: number,
        public nom?: string,
        public pointVictoire?: number,
        public pointNull?: number,
        public pointDefaite?: number,
        public pointForfait?: number,
        public saisonId?: number,
        public adversaires?: ITeam[],
        public journees?: IGame[]
    ) {}
}

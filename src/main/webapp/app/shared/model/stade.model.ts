export interface IStade {
    id?: number;
    nom?: string;
    adresse?: string;
    codePostal?: string;
    ville?: string;
}

export class Stade implements IStade {
    constructor(public id?: number, public nom?: string, public adresse?: string, public codePostal?: string, public ville?: string) {}
}

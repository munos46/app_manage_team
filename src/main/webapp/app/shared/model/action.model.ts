export const enum TypeAction {
    GOAL = 'GOAL',
    CARDYELOW = 'CARDYELOW',
    ATTACK = 'ATTACK',
    REPLACEMENT = 'REPLACEMENT',
    CARDRED = 'CARDRED'
}

export interface IAction {
    id?: number;
    typeAction?: TypeAction;
    minute?: number;
    prolongation?: boolean;
    commntary?: string;
    playerOneId?: number;
    playerTwoId?: number;
}

export class Action implements IAction {
    constructor(
        public id?: number,
        public typeAction?: TypeAction,
        public minute?: number,
        public prolongation?: boolean,
        public commntary?: string,
        public playerOneId?: number,
        public playerTwoId?: number
    ) {
        this.prolongation = false;
    }
}

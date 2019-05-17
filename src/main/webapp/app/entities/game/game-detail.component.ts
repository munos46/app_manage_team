import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGame } from 'app/shared/model/game.model';

@Component({
    selector: 'jhi-game-detail',
    templateUrl: './game-detail.component.html'
})
export class GameDetailComponent implements OnInit {
    game: IGame;

    constructor(private route: ActivatedRoute) {}

    ngOnInit() {
        this.route.data.subscribe(({ game }) => {
            this.game = game.body ? game.body : game;
        });
    }

    previousState() {
        window.history.back();
    }
}

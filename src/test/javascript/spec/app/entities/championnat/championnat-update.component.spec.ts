/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ManageTeamTestModule } from '../../../test.module';
import { ChampionnatUpdateComponent } from 'app/entities/championnat/championnat-update.component';
import { ChampionnatService } from 'app/entities/championnat/championnat.service';
import { Championnat } from 'app/shared/model/championnat.model';

import { SaisonService } from 'app/entities/saison';
import { TeamService } from 'app/entities/team';
import { GameService } from 'app/entities/game';

describe('Component Tests', () => {
    describe('Championnat Management Update Component', () => {
        let comp: ChampionnatUpdateComponent;
        let fixture: ComponentFixture<ChampionnatUpdateComponent>;
        let service: ChampionnatService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ManageTeamTestModule],
                declarations: [ChampionnatUpdateComponent],
                providers: [SaisonService, TeamService, GameService, ChampionnatService]
            })
                .overrideTemplate(ChampionnatUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChampionnatUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChampionnatService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Championnat(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.championnat = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Championnat();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.championnat = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

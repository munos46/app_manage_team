/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { ManageTeamTestModule } from '../../../test.module';
import { ChampionnatDetailComponent } from 'app/entities/championnat/championnat-detail.component';
import { Championnat } from 'app/shared/model/championnat.model';

describe('Component Tests', () => {
    describe('Championnat Management Detail Component', () => {
        let comp: ChampionnatDetailComponent;
        let fixture: ComponentFixture<ChampionnatDetailComponent>;
        const route = ({ data: of({ championnat: new Championnat(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ManageTeamTestModule],
                declarations: [ChampionnatDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ChampionnatDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChampionnatDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.championnat).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ManageTeamTestModule } from '../../../test.module';
import { ChampionnatComponent } from 'app/entities/championnat/championnat.component';
import { ChampionnatService } from 'app/entities/championnat/championnat.service';
import { Championnat } from 'app/shared/model/championnat.model';

describe('Component Tests', () => {
    describe('Championnat Management Component', () => {
        let comp: ChampionnatComponent;
        let fixture: ComponentFixture<ChampionnatComponent>;
        let service: ChampionnatService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ManageTeamTestModule],
                declarations: [ChampionnatComponent],
                providers: [ChampionnatService]
            })
                .overrideTemplate(ChampionnatComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChampionnatComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChampionnatService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                Observable.of(
                    new HttpResponse({
                        body: [new Championnat(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.championnats[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { ManageTeamTestModule } from '../../../test.module';
import { PractiseDetailComponent } from 'app/entities/practise/practise-detail.component';
import { Practise } from 'app/shared/model/practise.model';

describe('Component Tests', () => {
    describe('Practise Management Detail Component', () => {
        let comp: PractiseDetailComponent;
        let fixture: ComponentFixture<PractiseDetailComponent>;
        const route = ({ data: of({ practise: new Practise(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ManageTeamTestModule],
                declarations: [PractiseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(PractiseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PractiseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.practise).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

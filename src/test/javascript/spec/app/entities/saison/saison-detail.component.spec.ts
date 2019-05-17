/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs/observable/of';

import { ManageTeamTestModule } from '../../../test.module';
import { SaisonDetailComponent } from 'app/entities/saison/saison-detail.component';
import { Saison } from 'app/shared/model/saison.model';

describe('Component Tests', () => {
    describe('Saison Management Detail Component', () => {
        let comp: SaisonDetailComponent;
        let fixture: ComponentFixture<SaisonDetailComponent>;
        const route = ({ data: of({ saison: new Saison(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ManageTeamTestModule],
                declarations: [SaisonDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SaisonDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SaisonDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.saison).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

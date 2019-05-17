/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ManageTeamTestModule } from '../../../test.module';
import { SaisonComponent } from 'app/entities/saison/saison.component';
import { SaisonService } from 'app/entities/saison/saison.service';
import { Saison } from 'app/shared/model/saison.model';

describe('Component Tests', () => {
    describe('Saison Management Component', () => {
        let comp: SaisonComponent;
        let fixture: ComponentFixture<SaisonComponent>;
        let service: SaisonService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ManageTeamTestModule],
                declarations: [SaisonComponent],
                providers: [SaisonService]
            })
                .overrideTemplate(SaisonComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SaisonComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaisonService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                Observable.of(
                    new HttpResponse({
                        body: [new Saison(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.saisons[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

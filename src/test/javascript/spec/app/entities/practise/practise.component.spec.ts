/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ManageTeamTestModule } from '../../../test.module';
import { PractiseComponent } from 'app/entities/practise/practise.component';
import { PractiseService } from 'app/entities/practise/practise.service';
import { Practise } from 'app/shared/model/practise.model';

describe('Component Tests', () => {
    describe('Practise Management Component', () => {
        let comp: PractiseComponent;
        let fixture: ComponentFixture<PractiseComponent>;
        let service: PractiseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ManageTeamTestModule],
                declarations: [PractiseComponent],
                providers: [PractiseService]
            })
                .overrideTemplate(PractiseComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PractiseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PractiseService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                Observable.of(
                    new HttpResponse({
                        body: [new Practise(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.practises[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

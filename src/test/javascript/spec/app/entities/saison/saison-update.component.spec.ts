/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ManageTeamTestModule } from '../../../test.module';
import { SaisonUpdateComponent } from 'app/entities/saison/saison-update.component';
import { SaisonService } from 'app/entities/saison/saison.service';
import { Saison } from 'app/shared/model/saison.model';

describe('Component Tests', () => {
    describe('Saison Management Update Component', () => {
        let comp: SaisonUpdateComponent;
        let fixture: ComponentFixture<SaisonUpdateComponent>;
        let service: SaisonService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ManageTeamTestModule],
                declarations: [SaisonUpdateComponent],
                providers: [SaisonService]
            })
                .overrideTemplate(SaisonUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SaisonUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaisonService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Saison(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.saison = entity;
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
                    const entity = new Saison();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.saison = entity;
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

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ManageTeamTestModule } from '../../../test.module';
import { PractiseUpdateComponent } from 'app/entities/practise/practise-update.component';
import { PractiseService } from 'app/entities/practise/practise.service';
import { Practise } from 'app/shared/model/practise.model';

import { StadeService } from 'app/entities/stade';
import { PlayerService } from 'app/entities/player';
import { ManagerService } from 'app/entities/manager';

describe('Component Tests', () => {
    describe('Practise Management Update Component', () => {
        let comp: PractiseUpdateComponent;
        let fixture: ComponentFixture<PractiseUpdateComponent>;
        let service: PractiseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ManageTeamTestModule],
                declarations: [PractiseUpdateComponent],
                providers: [StadeService, PlayerService, ManagerService, PractiseService]
            })
                .overrideTemplate(PractiseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(PractiseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PractiseService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Practise(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.practise = entity;
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
                    const entity = new Practise();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.practise = entity;
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

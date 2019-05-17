/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { ManageTeamTestModule } from '../../../test.module';
import { ManagerUpdateComponent } from 'app/entities/manager/manager-update.component';
import { ManagerService } from 'app/entities/manager/manager.service';
import { Manager } from 'app/shared/model/manager.model';

import { UserService } from 'app/core';

describe('Component Tests', () => {
    describe('Manager Management Update Component', () => {
        let comp: ManagerUpdateComponent;
        let fixture: ComponentFixture<ManagerUpdateComponent>;
        let service: ManagerService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ManageTeamTestModule],
                declarations: [ManagerUpdateComponent],
                providers: [UserService, ManagerService]
            })
                .overrideTemplate(ManagerUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ManagerUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ManagerService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Manager(123);
                    spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.manager = entity;
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
                    const entity = new Manager();
                    spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({ body: entity })));
                    comp.manager = entity;
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

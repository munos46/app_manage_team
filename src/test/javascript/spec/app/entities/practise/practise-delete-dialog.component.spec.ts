/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ManageTeamTestModule } from '../../../test.module';
import { PractiseDeleteDialogComponent } from 'app/entities/practise/practise-delete-dialog.component';
import { PractiseService } from 'app/entities/practise/practise.service';

describe('Component Tests', () => {
    describe('Practise Management Delete Component', () => {
        let comp: PractiseDeleteDialogComponent;
        let fixture: ComponentFixture<PractiseDeleteDialogComponent>;
        let service: PractiseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ManageTeamTestModule],
                declarations: [PractiseDeleteDialogComponent],
                providers: [PractiseService]
            })
                .overrideTemplate(PractiseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PractiseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PractiseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});

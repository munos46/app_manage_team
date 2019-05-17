/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ManageTeamTestModule } from '../../../test.module';
import { StadeDeleteDialogComponent } from 'app/entities/stade/stade-delete-dialog.component';
import { StadeService } from 'app/entities/stade/stade.service';

describe('Component Tests', () => {
    describe('Stade Management Delete Component', () => {
        let comp: StadeDeleteDialogComponent;
        let fixture: ComponentFixture<StadeDeleteDialogComponent>;
        let service: StadeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ManageTeamTestModule],
                declarations: [StadeDeleteDialogComponent],
                providers: [StadeService]
            })
                .overrideTemplate(StadeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StadeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StadeService);
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

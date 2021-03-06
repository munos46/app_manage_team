/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ManageTeamTestModule } from '../../../test.module';
import { SaisonDeleteDialogComponent } from 'app/entities/saison/saison-delete-dialog.component';
import { SaisonService } from 'app/entities/saison/saison.service';

describe('Component Tests', () => {
    describe('Saison Management Delete Component', () => {
        let comp: SaisonDeleteDialogComponent;
        let fixture: ComponentFixture<SaisonDeleteDialogComponent>;
        let service: SaisonService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ManageTeamTestModule],
                declarations: [SaisonDeleteDialogComponent],
                providers: [SaisonService]
            })
                .overrideTemplate(SaisonDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SaisonDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaisonService);
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

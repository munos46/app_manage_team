/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ManageTeamTestModule } from '../../../test.module';
import { PlayerDeleteDialogComponent } from 'app/entities/player/player-delete-dialog.component';
import { PlayerService } from 'app/entities/player/player.service';

describe('Component Tests', () => {
    describe('Player Management Delete Component', () => {
        let comp: PlayerDeleteDialogComponent;
        let fixture: ComponentFixture<PlayerDeleteDialogComponent>;
        let service: PlayerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ManageTeamTestModule],
                declarations: [PlayerDeleteDialogComponent],
                providers: [PlayerService]
            })
                .overrideTemplate(PlayerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PlayerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PlayerService);
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

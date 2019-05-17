/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { ManageTeamTestModule } from '../../../test.module';
import { ChampionnatDeleteDialogComponent } from 'app/entities/championnat/championnat-delete-dialog.component';
import { ChampionnatService } from 'app/entities/championnat/championnat.service';

describe('Component Tests', () => {
    describe('Championnat Management Delete Component', () => {
        let comp: ChampionnatDeleteDialogComponent;
        let fixture: ComponentFixture<ChampionnatDeleteDialogComponent>;
        let service: ChampionnatService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ManageTeamTestModule],
                declarations: [ChampionnatDeleteDialogComponent],
                providers: [ChampionnatService]
            })
                .overrideTemplate(ChampionnatDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChampionnatDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChampionnatService);
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

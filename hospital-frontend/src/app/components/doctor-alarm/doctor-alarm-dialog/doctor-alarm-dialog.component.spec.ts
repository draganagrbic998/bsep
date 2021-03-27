import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorAlarmDialogComponent } from './doctor-alarm-dialog.component';

describe('DoctorAlarmDialogComponent', () => {
  let component: DoctorAlarmDialogComponent;
  let fixture: ComponentFixture<DoctorAlarmDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorAlarmDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DoctorAlarmDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

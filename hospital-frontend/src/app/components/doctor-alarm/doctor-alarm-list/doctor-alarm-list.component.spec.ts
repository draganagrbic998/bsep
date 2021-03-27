import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorAlarmListComponent } from './doctor-alarm-list.component';

describe('DoctorAlarmListComponent', () => {
  let component: DoctorAlarmListComponent;
  let fixture: ComponentFixture<DoctorAlarmListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorAlarmListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DoctorAlarmListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorAlarmDetailsComponent } from './doctor-alarm-details.component';

describe('DoctorAlarmDetailsComponent', () => {
  let component: DoctorAlarmDetailsComponent;
  let fixture: ComponentFixture<DoctorAlarmDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorAlarmDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DoctorAlarmDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

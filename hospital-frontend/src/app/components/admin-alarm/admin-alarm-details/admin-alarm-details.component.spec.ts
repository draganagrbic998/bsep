import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAlarmDetailsComponent } from './admin-alarm-details.component';

describe('AdminAlarmDetailsComponent', () => {
  let component: AdminAlarmDetailsComponent;
  let fixture: ComponentFixture<AdminAlarmDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminAlarmDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminAlarmDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

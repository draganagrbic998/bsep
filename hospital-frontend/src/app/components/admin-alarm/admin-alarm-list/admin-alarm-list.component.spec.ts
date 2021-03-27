import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAlarmListComponent } from './admin-alarm-list.component';

describe('AdminAlarmListComponent', () => {
  let component: AdminAlarmListComponent;
  let fixture: ComponentFixture<AdminAlarmListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminAlarmListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminAlarmListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminAlarmDialogComponent } from './admin-alarm-dialog.component';

describe('AdminAlarmDialogComponent', () => {
  let component: AdminAlarmDialogComponent;
  let fixture: ComponentFixture<AdminAlarmDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminAlarmDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminAlarmDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

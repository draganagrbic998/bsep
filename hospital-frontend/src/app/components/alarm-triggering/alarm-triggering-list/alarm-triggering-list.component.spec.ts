import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlarmTriggeringListComponent } from './alarm-triggering-list.component';

describe('AlarmTriggeringListComponent', () => {
  let component: AlarmTriggeringListComponent;
  let fixture: ComponentFixture<AlarmTriggeringListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AlarmTriggeringListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AlarmTriggeringListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

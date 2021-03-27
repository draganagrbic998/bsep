import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlarmTriggeringDetailsComponent } from './alarm-triggering-details.component';

describe('AlarmTriggeringDetailsComponent', () => {
  let component: AlarmTriggeringDetailsComponent;
  let fixture: ComponentFixture<AlarmTriggeringDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AlarmTriggeringDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AlarmTriggeringDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

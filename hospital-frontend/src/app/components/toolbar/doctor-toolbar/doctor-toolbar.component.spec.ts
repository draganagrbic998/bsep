import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorToolbarComponent } from './doctor-toolbar.component';

describe('DoctorToolbarComponent', () => {
  let component: DoctorToolbarComponent;
  let fixture: ComponentFixture<DoctorToolbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorToolbarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DoctorToolbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { DoctorAlarmService } from './doctor-alarm.service';

describe('DoctorAlarmService', () => {
  let service: DoctorAlarmService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DoctorAlarmService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

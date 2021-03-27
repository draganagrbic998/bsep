import { TestBed } from '@angular/core/testing';

import { AdminAlarmService } from './admin-alarm.service';

describe('AdminAlarmService', () => {
  let service: AdminAlarmService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AdminAlarmService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

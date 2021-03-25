import { TestBed } from '@angular/core/testing';

import { AlarmTriggeringService } from './alarm-triggering.service';

describe('AlarmTriggeringService', () => {
  let service: AlarmTriggeringService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AlarmTriggeringService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

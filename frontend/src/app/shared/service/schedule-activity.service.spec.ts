import { TestBed } from '@angular/core/testing';

import { ScheduleActivityService } from './schedule-activity.service';

describe('ScheduleActivityService', () => {
  let service: ScheduleActivityService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ScheduleActivityService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

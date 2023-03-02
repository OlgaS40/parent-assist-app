import { TestBed } from '@angular/core/testing';

import { EventMilestoneService } from './event-milestone.service';

describe('EventMilestoneService', () => {
  let service: EventMilestoneService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventMilestoneService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

import { TestBed } from '@angular/core/testing';

import { EventActivityService } from './event-activity.service';

describe('EventActivityService', () => {
  let service: EventActivityService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventActivityService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

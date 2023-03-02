import { TestBed } from '@angular/core/testing';

import { CashInService } from './cash-in.service';

describe('CashInService', () => {
  let service: CashInService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CashInService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

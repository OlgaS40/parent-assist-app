import { TestBed } from '@angular/core/testing';

import { PlanPricingService } from './plan-pricing.service';

describe('PlanPricingService', () => {
  let service: PlanPricingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlanPricingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

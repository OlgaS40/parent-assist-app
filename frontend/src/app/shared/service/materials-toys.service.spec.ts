import { TestBed } from '@angular/core/testing';

import { MaterialsToysService } from './materials-toys.service';

describe('MaterialsToysService', () => {
  let service: MaterialsToysService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MaterialsToysService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

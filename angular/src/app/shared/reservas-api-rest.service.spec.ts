import { TestBed } from '@angular/core/testing';

import { ReservasApiRestService } from './reservas-api-rest.service';

describe('ReservasApiRestService', () => {
  let service: ReservasApiRestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ReservasApiRestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

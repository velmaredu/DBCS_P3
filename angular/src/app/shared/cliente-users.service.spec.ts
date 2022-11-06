import { TestBed } from '@angular/core/testing';

import { ClienteUsersService } from './cliente-users.service';

describe('ClienteUsersService', () => {
  let service: ClienteUsersService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ClienteUsersService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

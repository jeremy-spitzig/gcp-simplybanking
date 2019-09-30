import { TestBed } from '@angular/core/testing';

import { TransactionLogService } from './transaction-log.service';

describe('TransactionLogService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TransactionLogService = TestBed.get(TransactionLogService);
    expect(service).toBeTruthy();
  });
});

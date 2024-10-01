import { TestBed } from '@angular/core/testing';

import { ExamInstructionsService } from './exam-instructions.service';

describe('ExamInstructionsService', () => {
  let service: ExamInstructionsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExamInstructionsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

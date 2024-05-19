import { TestBed } from '@angular/core/testing';

import { RecetaIngredienteService } from './receta-ingrediente.service';

describe('RecetaIngredienteService', () => {
  let service: RecetaIngredienteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RecetaIngredienteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

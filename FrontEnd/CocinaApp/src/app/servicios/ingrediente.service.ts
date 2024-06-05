// src/app/servicios/ingrediente.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ingrediente } from '../clases/ingrediente';
import { environment } from '../../environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class IngredienteService {
  private apiUrl = environment.urlApi + '/ingredientes'; // Asegúrate de que esta URL es correcta

  constructor(private http: HttpClient) { }

  obtenerIngredientes(): Observable<Ingrediente[]> {
    return this.http.get<Ingrediente[]>(this.apiUrl);
  }
}
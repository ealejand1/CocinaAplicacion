// src/app/servicios/ingrediente.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Ingrediente } from '../clases/ingrediente';

@Injectable({
  providedIn: 'root'
})
export class IngredienteService {
  private apiUrl = 'http://localhost:8080/ingredientes'; // Asegúrate de que esta URL es correcta

  constructor(private http: HttpClient) { }

  obtenerIngredientes(): Observable<Ingrediente[]> {
    return this.http.get<Ingrediente[]>(this.apiUrl);
  }
}
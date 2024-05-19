// src/app/services/receta-ingrediente.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RecetaIngrediente } from '../clases/receta-ingrediente';


@Injectable({
  providedIn: 'root'
})
export class RecetaIngredienteService {
  private apiUrl = 'http://localhost:8080/recetas-ingredientes';

  constructor(private http: HttpClient) {}

  obtenerIngredientesPorReceta(recetaId: number): Observable<RecetaIngrediente[]> {
    return this.http.get<RecetaIngrediente[]>(`${this.apiUrl}/receta/${recetaId}`);
  }
}
// src/app/services/receta-ingrediente.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Receta } from '../clases/receta';
import { RecetaIngrediente } from '../clases/receta-ingrediente';


@Injectable({
  providedIn: 'root'
})
export class RecetaIngredienteService {
  private apiUrl = environment.urlApi + '/recetas-ingredientes';

  constructor(private http: HttpClient) {}

  obtenerIngredientesPorReceta(recetaId: number): Observable<RecetaIngrediente[]> {
    return this.http.get<RecetaIngrediente[]>(`${this.apiUrl}/receta/${recetaId}`);
  }
  obtenerRecetasPorIngrediente(ingredienteId: number): Observable<RecetaIngrediente[]>{
    return this.http.get<RecetaIngrediente[]>(`${this.apiUrl}/ingrediente/${ingredienteId}`);
  }
}
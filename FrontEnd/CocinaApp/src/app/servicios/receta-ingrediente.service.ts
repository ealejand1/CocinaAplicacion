// src/app/services/receta-ingrediente.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RecetaIngrediente } from '../clases/receta-ingrediente';
import { environment } from '../../environments/environment';


@Injectable({
  providedIn: 'root'
})
export class RecetaIngredienteService {
  private apiUrl = environment.urlApi + '/recetas-ingredientes';

  constructor(private http: HttpClient) {}

  obtenerIngredientesPorReceta(recetaId: number): Observable<RecetaIngrediente[]> {
    return this.http.get<RecetaIngrediente[]>(`${this.apiUrl}/receta/${recetaId}`);
  }
  crearRecetaIngrediente(recetaIngrediente: RecetaIngrediente): Observable<any> {
    return this.http.post<RecetaIngrediente>(this.apiUrl, recetaIngrediente);
  }
  //anadir getall recetas
}
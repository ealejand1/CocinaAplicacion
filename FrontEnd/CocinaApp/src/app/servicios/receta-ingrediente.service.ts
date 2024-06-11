// src/app/services/receta-ingrediente.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
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
  crearRecetaIngrediente(recetaIngrediente: any): Observable<any> {
    return this.http.post<RecetaIngrediente>(this.apiUrl, recetaIngrediente);
  }
  //anadir getall recetas
  obtenerRecetasPorIngrediente(ingredienteId: number): Observable<RecetaIngrediente[]>{
    return this.http.get<RecetaIngrediente[]>(`${this.apiUrl}/ingrediente/${ingredienteId}`);
  }
  // En src/app/services/receta-ingrediente.service.ts
actualizarRecetaIngrediente(ingredienteId: number, recetaIngrediente: RecetaIngrediente): Observable<RecetaIngrediente> {
  return this.http.put<RecetaIngrediente>(`${this.apiUrl}/${ingredienteId}`, recetaIngrediente);
}

} 
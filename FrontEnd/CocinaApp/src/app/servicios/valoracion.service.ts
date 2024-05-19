import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Valoracion } from '../clases/valoracion'; // Asegúrate de que el modelo Valoracion esté correctamente definido.

@Injectable({
  providedIn: 'root'
})
export class ValoracionService {
  private apiUrl = 'http://localhost:8080'; // Asegúrate de que esta URL coincide con la de tu backend

  constructor(private http: HttpClient) { }

  obtenerValoracionesPorReceta(recetaId: number): Observable<Valoracion[]> {
    return this.http.get<Valoracion[]>(`${this.apiUrl}/recetas/${recetaId}/valoraciones`);
  }
}
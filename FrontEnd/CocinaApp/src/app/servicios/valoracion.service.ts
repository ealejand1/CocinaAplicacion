import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Valoracion } from '../clases/valoracion'; // Asegúrate de que el modelo Valoracion esté correctamente definido.
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ValoracionService {
  private apiUrl = environment.urlApi + '/valoraciones'; // Asegúrate de que esta URL coincide con la de tu backend

  constructor(private http: HttpClient) { }

  obtenerValoracionesPorReceta(recetaId: number): Observable<Valoracion[]> {
    return this.http.get<Valoracion[]>(`${this.apiUrl}/${recetaId}/valoraciones`);
  }
}
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Valoracion } from '../clases/valoracion'; // Asegúrate de que el modelo Valoracion esté correctamente definido.
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ValoracionService {
  private apiUrl = environment.urlApi + '/valoraciones';

  constructor(private http: HttpClient) { }

  obtenerValoracionesPorReceta(recetaId: number): Observable<Valoracion[]> {
    return this.http.get<Valoracion[]>(`${this.apiUrl}/${recetaId}/valoraciones`);
  }
  crearValoracion(valoracion: Valoracion): Observable<Valoracion> {
    return this.http.post<Valoracion>(this.apiUrl, valoracion);
  }
  obtenerTodasLasValoraciones(): Observable<Valoracion[]> {
    return this.http.get<Valoracion[]>(this.apiUrl);
  }
  
}
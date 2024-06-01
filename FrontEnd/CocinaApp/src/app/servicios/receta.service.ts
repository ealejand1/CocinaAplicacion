import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Receta } from '../clases/receta';
import { Valoracion } from '../clases/valoracion';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RecetaService {

  private apiUrl = environment.urlApi + '/recetas'; // URL del backend

  constructor(private http: HttpClient) { }

  obtenerRecetas(): Observable<Receta[]> {
    return this.http.get<Receta[]>(this.apiUrl);
  }
  obtenerRecetaPorId(id: number): Observable<Receta> {
    return this.http.get<Receta>(`${this.apiUrl}/${id}`);
  } 
  obtenerValoracionesPorReceta(idReceta: number): Observable<Valoracion[]> {
    return this.http.get<Valoracion[]>(`${this.apiUrl}/${idReceta}/valoraciones`);
  }
  actualizarReceta(id: number, receta: Receta): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, receta);
  }
  crearReceta(receta: Receta): Observable<Object> {
    return this.http.post(this.apiUrl, receta);
  }
  eliminarReceta(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
  obtenerRecetasPorCategoria(idCategoria: number): Observable<Receta[]>{
    return this.http.get<Receta[]>(`${this.apiUrl}/categoria/${idCategoria}/recetas`);
  }
  obtenerRecetasPorNombre(nombreReceta:String):Observable<Receta[]>{
    return this.http.get<Receta[]>(`${this.apiUrl}/nombre/${nombreReceta}`);
  }
}

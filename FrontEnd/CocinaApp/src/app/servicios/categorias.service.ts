import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Categoria } from '../clases/categoria';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CategoriasService {

  //Listado de categorias
  private baseURL =  environment.urlApi + "/categorias";

  constructor(private httpClient : HttpClient) { }

  //Obtener categorias
  obtenerListaCategorias():Observable<Categoria[]>{
    return this.httpClient.get<Categoria[]>(this.baseURL);
  }

  obtenerCategoriaPorId(id:number):Observable<Categoria>{
    return this.httpClient.get<Categoria>(`${this.baseURL}/${id}`)
  }
  agregarRecetaACategoria(categoriaId: number, recetaId: number): Observable<any> {
    return this.httpClient.post(`${this.baseURL}/${categoriaId}/recetas/${recetaId}`, null); // Cambia aquí si es necesario
  }
  

}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Categoria } from '../clases/categoria';

@Injectable({
  providedIn: 'root'
})
export class CategoriasService {

  //Listado de categorias
  private baseURL = "http://localhost:8080/categorias";

  constructor(private httpClient : HttpClient) { }

  //Obtener categorias
  obtenerListaCategorias():Observable<Categoria[]>{
    return this.httpClient.get<Categoria[]>(this.baseURL);
  }

  obtenerCategoriaPorId(id:number):Observable<Categoria>{
    return this.httpClient.get<Categoria>(`${this.baseURL}/${id}`)
  }

}

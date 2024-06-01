import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  constructor(private http:HttpClient) { }


  obtenerRolporIdUsuario(idUsuario:number):Observable<any>{
    return this.http.get<any>(environment.urlApi + "/usuarios/"+ idUsuario + "/rol");
  }

}

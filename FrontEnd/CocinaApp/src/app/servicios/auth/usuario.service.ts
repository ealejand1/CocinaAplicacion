import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private rolSource = new BehaviorSubject<any>(null);
  rolActual = this.rolSource.asObservable();

  constructor(private http:HttpClient) { }


  obtenerRolporIdUsuario(idUsuario:number):Observable<any>{
    return this.http.get<any>(environment.urlApi + "/usuarios/"+ idUsuario + "/rol");
  }

  cambiarRol(nuevoRol:string){
    this.rolSource.next(nuevoRol);
  }

}

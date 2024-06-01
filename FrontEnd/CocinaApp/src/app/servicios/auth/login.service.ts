import { Injectable } from '@angular/core';
import { LoginRequest } from './loginRequest';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, Observable, throwError,BehaviorSubject,tap, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { registroRequest } from './registroRequest';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  //Este boolean nos sirve para poder inidicar si un usuario ha iniciado sesion
  //El behaviorSubject nos sirve para poder ver si se ha porducido un cambio en una API

  constructor(private http:HttpClient) { 
    //this.currentUser = new BehaviorSubject<boolean>(sessionStorage.getItem("token") != null); 
    //this.currentUserData = new BehaviorSubject<String>(sessionStorage.getItem("token") || "");
  }


  login(datos:LoginRequest):Observable<any>{
    return this.http.post<any>(environment.urlHost + "/auth/login",datos);
  }

  loginInvitado():Observable<any>{
    return this.http.post<any>(environment.urlHost + "/auth/loginInvitado","a");
  }

  registroUsuario(datosRegistro:registroRequest):Observable<any>{
    return this.http.post<any>(environment.urlHost + "/auth/registro",datosRegistro)
  }

  logout():void{
    localStorage.removeItem("token");
    localStorage.removeItem("idUsuario");
  }

  //Manejador de Errores
  private handleError(error:HttpErrorResponse){
    if(error.status == 0){
      console.error("Se ha producido un error", error.error)
    }
    else{
      console.error("Codigo de Estado que devolvio el BackEnd ",error)
    }
    return throwError(()=> new Error('Fallo algo'));

  }

}

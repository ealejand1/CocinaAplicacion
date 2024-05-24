import { Injectable } from '@angular/core';
import { LoginRequest } from './loginRequest';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { catchError, Observable, throwError,BehaviorSubject,tap, map } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  //Este boolean nos sirve para poder inidicar si un usuario ha iniciado sesion
  //El behaviorSubject nos sirve para poder ver si se ha porducido un cambio en una API
  currentUser : BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  currentUserData : BehaviorSubject<String> = new BehaviorSubject<String>("");


  constructor(private http:HttpClient) { 
    //this.currentUser = new BehaviorSubject<boolean>(sessionStorage.getItem("token") != null); 
    //this.currentUserData = new BehaviorSubject<String>(sessionStorage.getItem("token") || "");
  }


  login2(datos:LoginRequest):Observable<any>{
    return this.http.post<any>(environment.urlHost + "/auth/login",datos);
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

  get userData(): Observable<String>{
    return this.currentUserData.asObservable();
  }
  get userLogin(): Observable<boolean>{
    return this.currentUser.asObservable();
  }

}

import { Injectable } from '@angular/core';
import { LoginRequest } from './loginRequest';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import {
  catchError,
  Observable,
  throwError,
  BehaviorSubject,
  tap,
  map,
} from 'rxjs';
import { environment } from '../../../environments/environment';
import { registroRequest } from './registroRequest';
import { User } from './user';
import { Router } from '@angular/router';
import { UsuarioService } from './usuario.service';
import { error } from 'console';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private user = new BehaviorSubject<User | null>(null);
  userActual = this.user.asObservable();
  isLogging: Observable<boolean> = this.userActual.pipe(map(Boolean));
  //Este boolean nos sirve para poder inidicar si un usuario ha iniciado sesion
  //El behaviorSubject nos sirve para poder ver si se ha porducido un cambio en una API

  constructor(private http: HttpClient, private router: Router,private usuarioServicio:UsuarioService) {
    this.cargarUsuarioLocalStorage();
  }

  login(datos: LoginRequest): Observable<any> {
    return this.http.post<any>(environment.urlHost + '/auth/login', datos).pipe(
      tap((user) => this.guardarDatosLocalStorage(user.token, user.id)),
      tap((user) => this.loginNuevoUsuario(user.id,user.token,))
    );
  }

  loginInvitado(): Observable<any> {
    return this.http
      .post<any>(environment.urlHost + '/auth/loginInvitado', 'a')
      .pipe(
        tap((userInvitado) => {
          this.guardarDatosLocalStorage(userInvitado.token, userInvitado.id);
        }),
        tap((user) => this.loginNuevoUsuario(user.id,user.token))
      );
  }

  registroUsuario(datosRegistro: registroRequest): Observable<any> {
    return this.http.post<any>(
      environment.urlHost + '/auth/registro',
      datosRegistro
    );
  }

  logout(): void {
    localStorage.clear();
    this.user.next(null);
    this.router.navigateByUrl('login');
  }

  private guardarDatosLocalStorage(token: string, idUsuario: string): void {
    localStorage.setItem('token', token);
    localStorage.setItem('idUsuario', idUsuario);
  }

  private loginNuevoUsuario(idUsuario: any, token: string) {
    this.usuarioServicio.obtenerRolporIdUsuario(idUsuario).subscribe(
      (response) => {  
        const newUser: User = {
          id: idUsuario,
          token: token,
          rol: response
        };
        this.user.next(newUser);
      },
      error => console.log("Obtener Rol Usuario",error)
    )
  }

  private isLocalStorageAvailable(): boolean {
    return typeof window !== 'undefined' && typeof localStorage !== 'undefined';
  }

  private cargarUsuarioLocalStorage(): void {
    if (this.isLocalStorageAvailable()) {
      const userLocalToken = localStorage.getItem('token');
      const userLocalID = localStorage.getItem('idUsuario');

      userLocalID && userLocalToken && this.loginNuevoUsuario(userLocalID, userLocalToken);
    }
  }

  //Manejador de Errores
  private handleError(error: HttpErrorResponse) {
    if (error.status == 0) {
      console.error('Se ha producido un error', error.error);
    } else {
      console.error('Codigo de Estado que devolvio el BackEnd ', error);
    }
    return throwError(() => new Error('Fallo algo'));
  }
}

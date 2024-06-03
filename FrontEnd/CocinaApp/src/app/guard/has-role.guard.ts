import { inject, Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanLoad, GuardResult, MaybeAsync, Route, RouterStateSnapshot, UrlSegment } from '@angular/router';
import { LoginService } from '../servicios/auth/login.service';
import { map, Observable, tap } from 'rxjs';
import { Role } from '../servicios/auth/Role';

@Injectable({
  providedIn: 'root',
})

export class hasRoleGuard implements CanLoad,CanActivate{

  constructor(private loginService:LoginService){

  }
  canActivate(route: ActivatedRouteSnapshot): Observable<boolean> {
    return this.hasRole(route);
  }
  canLoad(route: Route): Observable<boolean> {
    return this.hasRole(route);
  }
  
  private hasRole(route:Route | ActivatedRouteSnapshot){
    const allowedRoles = route.data?.['allowedRoles'];

    return this.loginService.userActual.pipe(
      map((user)=> Boolean(user && allowedRoles.includes(user.rol))),
      tap((hasRole)=> hasRole === false && alert('ACCESO DENEGADO'))
    )
  }
}

export function hasRole(allowedRoles:Role[]){
  return () =>{
    return inject(LoginService).userActual.pipe(
      map((user)=> Boolean(user && allowedRoles.includes(user.rol))),
      tap((hasRole)=> hasRole === false && alert('ACCESO DENEGADO'))
    );
  }
}
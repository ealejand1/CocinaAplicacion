import {
  Directive,
  Input,
  OnDestroy,
  OnInit,
  TemplateRef,
  ViewContainerRef,
} from '@angular/core';
import { Role } from '../servicios/auth/Role';
import { LoginService } from '../servicios/auth/login.service';
import { distinctUntilChanged, map, Subscription, tap } from 'rxjs';

@Directive({
  selector: '[appMostrarRoles]',
})
export class MostrarRolesDirective implements OnInit, OnDestroy {
  @Input('appMostrarRoles') allowdRoles?: Role[];
  private sub?: Subscription;

  constructor(
    private loginService: LoginService,
    private viewContainerRef: ViewContainerRef,
    private templateRef: TemplateRef<any>
  ) {}

  ngOnInit(): void {
    this.sub = this.loginService.userActual
      .pipe(
        map(user => {
          return Boolean(user && this.allowdRoles?.includes(user.rol));
        }),
        distinctUntilChanged(),
        tap(hasRole => {
          console.log('Has role:', hasRole);
          hasRole ? this.viewContainerRef.createEmbeddedView(this.templateRef) : this.viewContainerRef.clear();
        })
      )
      .subscribe();
  }

  ngOnDestroy(): void {
    this.sub?.unsubscribe();
  }
}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RecetaDetalleComponent } from './receta-detalle/receta-detalle.component';
import { RecetasComponent } from './recetas/recetas.component';
import { InicioComponent } from './inicio/inicio.component';
import { LoginComponent } from './login/login.component';
import { loginGuard } from './guard/login.guard';
import { EditarRecetaComponent } from './editar-receta/editar-receta.component';
import { RegistrarRecetaComponent } from './registrar-receta/registrar-receta.component';
import { CategoriaRecetasComponent } from './categoria-recetas/categoria-recetas.component';
import { RegistroComponent } from './registro/registro.component';
import { BuscadorComponent } from './buscador/buscador.component';
import { hasRole, hasRoleGuard } from './guard/has-role.guard';

const routes: Routes = [
  {path: 'recetas', component: RecetasComponent,
    canActivate: [hasRole(['ADMIN','USER'])],
    canLoad:[hasRole(['ADMIN','USER'])],
   },
  {path: 'recetas/:id', component: RecetaDetalleComponent,
    canActivate: [hasRole(['ADMIN','USER','INVITADO'])],
    canLoad:[hasRole(['ADMIN','USER','INVITADO'])],
   },
  {path : "inicio",component:InicioComponent,  
  canActivate:[loginGuard]},
  {path : 'categoria/:id', component:CategoriaRecetasComponent,
    canActivate: [hasRole(['ADMIN','USER','INVITADO'])],
    canLoad:[hasRole(['ADMIN','USER','INVITADO'])],
  },
  { path: 'editar-receta/:id', component: EditarRecetaComponent,
    canActivate: [hasRole(['ADMIN','USER'])],
    canLoad:[hasRole(['ADMIN','USER'])],
  },
  {path : "registro",component:RegistroComponent},
  { path: 'registrar-receta', component: RegistrarRecetaComponent,
    canActivate: [hasRole(['ADMIN','USER','INVITADO'])],
    canLoad:[hasRole(['ADMIN','USER','INVITADO'])],
  },
  {path : "login",component:LoginComponent},
  {path : 'buscador',component:BuscadorComponent,
    canActivate: [hasRole(['ADMIN','USER','INVITADO'])],
    canLoad:[hasRole(['ADMIN','USER','INVITADO'])],},
  {path : '', redirectTo:"inicio",pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

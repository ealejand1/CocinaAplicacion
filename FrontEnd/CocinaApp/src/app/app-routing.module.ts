import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RecetaDetalleComponent } from './receta-detalle/receta-detalle.component';
import { RecetasComponent } from './recetas/recetas.component';
import { InicioComponent } from './inicio/inicio.component';
import { LoginComponent } from './login/login.component';
import { loginGuard } from './guard/login.guard';

const routes: Routes = [
  //{path: 'recetas', component: RecetasComponent },
  //{path: 'recetas/:id', component: RecetaDetalleComponent },
  {path : "inicio",component:InicioComponent,  
  canActivate:[loginGuard]},
  {path : "login",component:LoginComponent},
  {path : '', redirectTo:"inicio",pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

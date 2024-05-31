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
import { BuscadorComponent } from './buscador/buscador.component';

const routes: Routes = [
  {path: 'recetas', component: RecetasComponent },
  {path: 'recetas/:id', component: RecetaDetalleComponent },
  {path : "inicio",component:InicioComponent,  
  canActivate:[loginGuard]},
  {path : 'categoria/:id', component:CategoriaRecetasComponent},
  { path: 'editar-receta/:id', component: EditarRecetaComponent },
  { path: 'registrar-receta', component: RegistrarRecetaComponent},
  {path : "login",component:LoginComponent},
  {path : 'buscador',component:BuscadorComponent},
  {path : '', redirectTo:"inicio",pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

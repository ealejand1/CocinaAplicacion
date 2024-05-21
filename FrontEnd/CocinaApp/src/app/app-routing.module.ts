import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RecetaDetalleComponent } from './receta-detalle/receta-detalle.component';
import { RecetasComponent } from './recetas/recetas.component';
import { InicioComponent } from './inicio/inicio.component';
import { EditarRecetaComponent } from './editar-receta/editar-receta.component';
import { RegistrarRecetaComponent } from './registrar-receta/registrar-receta.component';

const routes: Routes = [
  {path: 'recetas', component: RecetasComponent },
  {path: 'recetas/:id', component: RecetaDetalleComponent },
  {path : "inicio",component:InicioComponent},
  { path: 'editar-receta/:id', component: EditarRecetaComponent },
  { path: 'registrar-receta', component: RegistrarRecetaComponent},
  {path : '', redirectTo:"inicio",pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

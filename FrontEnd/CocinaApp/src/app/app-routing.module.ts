import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RecetaDetalleComponent } from './receta-detalle/receta-detalle.component';
import { RecetasComponent } from './recetas/recetas.component';
import { InicioComponent } from './inicio/inicio.component';
import { CategoriaRecetasComponent } from './categoria-recetas/categoria-recetas.component';

const routes: Routes = [
  {path: 'recetas', component: RecetasComponent },
  {path: 'recetas/:id', component: RecetaDetalleComponent },
  {path : "inicio",component:InicioComponent},
  {path : '', redirectTo:"inicio",pathMatch: 'full'},
  {path : 'categoria/:id', component:CategoriaRecetasComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

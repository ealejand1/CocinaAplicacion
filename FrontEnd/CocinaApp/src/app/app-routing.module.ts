import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RecetaDetalleComponent } from './receta-detalle/receta-detalle.component';
import { RecetasComponent } from './recetas/recetas.component';

const routes: Routes = [
  { path: 'recetas', component: RecetasComponent },
  { path: 'recetas/:id', component: RecetaDetalleComponent },
  { path: '', redirectTo: '/recetas', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

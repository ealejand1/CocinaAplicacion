import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { RecetasComponent } from './recetas/recetas.component';
import { RecetaDetalleComponent } from './receta-detalle/receta-detalle.component';
import { InicioComponent } from './inicio/inicio.component';
import { HttpClientModule } from '@angular/common/http';
import { RecetasCategoriaComponent } from './recetas-categoria/recetas-categoria.component';
import { EditarRecetaComponent } from './editar-receta/editar-receta.component';
import { RegistrarRecetaComponent } from './registrar-receta/registrar-receta.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HeaderComponent,
    FooterComponent,
    RecetasComponent,
    RecetaDetalleComponent,
    InicioComponent,
    RecetasCategoriaComponent,
    EditarRecetaComponent,
    RegistrarRecetaComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
    provideClientHydration()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

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
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { RecetasCategoriaComponent } from './recetas-categoria/recetas-categoria.component';
import { EditarRecetaComponent } from './editar-receta/editar-receta.component';
import { RegistrarRecetaComponent } from './registrar-receta/registrar-receta.component';
import { CategoriaRecetasComponent } from './categoria-recetas/categoria-recetas.component';
import { AuthInterceptor } from './servicios/auth/auth.interceptor.service';
import { RegistroComponent } from './registro/registro.component';
import { BuscadorComponent } from './buscador/buscador.component';
import { MostrarRolesDirective } from './directive/mostrar-roles.directive';


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
    CategoriaRecetasComponent,
    RegistroComponent,
    BuscadorComponent,
    MostrarRolesDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    provideClientHydration()
  ],
  bootstrap: [AppComponent],
  exports: [
    MostrarRolesDirective
  ]
})
export class AppModule { }

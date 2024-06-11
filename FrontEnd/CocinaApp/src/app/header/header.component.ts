import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { LoginService } from '../servicios/auth/login.service';
import { UsuarioService } from '../servicios/auth/usuario.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']

})
export class HeaderComponent implements OnInit{

  loggeado:boolean = false;
  mostrarCrearRecetaDesplegable: boolean = false;
  mostrarCerrarSesionDesplegable: boolean = false;
  constructor(private router:Router, private loginServicio:LoginService){}

  ngOnInit(): void {
  }

  
  crearReceta(){
    this.router.navigate(['registrar-receta'])
  }

  logOut():void{
    this.loginServicio.logout();
  }

  toggleCrearRecetaDesplegable(): void {
    this.mostrarCrearRecetaDesplegable = !this.mostrarCrearRecetaDesplegable;
    if (this.mostrarCrearRecetaDesplegable) {
      this.mostrarCerrarSesionDesplegable = false; // Para cerrar el otro desplegable si estaba abierto
    }
  }

  toggleCerrarSesionDesplegable(): void {
    this.mostrarCerrarSesionDesplegable = !this.mostrarCerrarSesionDesplegable;
    if (this.mostrarCerrarSesionDesplegable) {
      this.mostrarCrearRecetaDesplegable = false; // Para cerrar el otro desplegable si estaba abierto
    }
  }
  cerrarDesplegable(menu: string) {
    if (menu === 'crearReceta') {
      this.mostrarCrearRecetaDesplegable = false;
    } else if (menu === 'cerrarSesion') {
      this.mostrarCerrarSesionDesplegable = false;
    }
  }

}

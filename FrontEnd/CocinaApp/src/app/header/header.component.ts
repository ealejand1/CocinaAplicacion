import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { LoginService } from '../servicios/auth/login.service';

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
    if (typeof window !== 'undefined' && window.localStorage) {
      if(localStorage.getItem("token")){
        this.loggeado = true;
       }
       else{
        this.loggeado = false;
       }
    }
  }

  crearReceta(){
    this.router.navigate(['registrar-receta'])
  }

  logOut():void{
    this.loginServicio.logout();
    this.router.navigateByUrl("login");
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

}

import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { LoginService } from '../servicios/auth/login.service';
import { UsuarioService } from '../servicios/auth/usuario.service';
import Swal from 'sweetalert2';

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

  logOut(): void {
    Swal.fire({
      title: '¿Nos dejas ya?',
      text: "Esperamos que vuelvas pronto para más aventuras culinarias!",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, cerrar sesión',
      cancelButtonText: 'Me quedo un poco más'
    }).then((result) => {
      if (result.isConfirmed) {
        this.loginServicio.logout();
        this.router.navigate(['/login']);
        Swal.fire(
          '¡Hasta pronto!',
          'Tu sesión ha sido cerrada. ¡Vuelve pronto!',
          'success'
        );
      }
    });
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

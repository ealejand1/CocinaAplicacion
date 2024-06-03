import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { LoginService } from '../servicios/auth/login.service';
import { UsuarioService } from '../servicios/auth/usuario.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'

})
export class HeaderComponent implements OnInit{

  loggeado:boolean = false;
  rol:String;
  constructor(private router:Router, private loginServicio:LoginService){}

  ngOnInit(): void {
  }

  
  crearReceta(){
    this.router.navigate(['registrar-receta'])
  }
  logOut():void{
    this.loginServicio.logout();
  }
}

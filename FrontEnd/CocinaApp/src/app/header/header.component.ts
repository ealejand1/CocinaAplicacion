import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { LoginService } from '../servicios/auth/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'

})
export class HeaderComponent implements OnInit{

  loggeado:boolean = false;
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
}

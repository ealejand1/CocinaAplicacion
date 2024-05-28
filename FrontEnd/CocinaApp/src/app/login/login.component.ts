import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { LoginService } from '../servicios/auth/login.service';
import { LoginRequest } from '../servicios/auth/loginRequest';
import { error } from 'console';
import { Router } from '@angular/router';
import { response } from 'express';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  ruta: string='../assets/vegetables.jpg'
  
  loginForm = this.formBuilder.group({
    username:[''],
    password:['']
  })


  constructor(private router:Router,private formBuilder:FormBuilder,private loginService:LoginService){

  }
  
  ngOnInit(): void {
  }

  login(){
    this.loginService.login(this.loginForm.value as LoginRequest).subscribe(
      response => {
        localStorage.setItem("token",response.token);
        localStorage.setItem("idUsuario",response.id);
        this.router.navigateByUrl("inicio");
      },
    )
  }

  loginInvitado(){
    this.loginService.loginInvitado().subscribe(
      response =>{
        localStorage.setItem("token",response.token);
        localStorage.setItem("idUsuario",response.id);
        this.router.navigateByUrl("inicio");
      }
    )
  }

  registrarse(){
    
  }
}

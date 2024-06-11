import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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
  loginForm : FormGroup;
  
  showError: boolean = false;


  constructor(private router:Router,private formBuilder:FormBuilder,private loginService:LoginService){

  }
  
  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: [''],
        password: ['']
    });
  }


  login(){
      this.loginService.login(this.loginForm.value as LoginRequest).subscribe(
        response => {
          this.router.navigateByUrl("inicio");
        },
        error => {
          this.showError = true;
        }
      )
  
  }

  loginInvitado(){
    this.loginService.loginInvitado().subscribe(
      response =>{
        this.router.navigateByUrl("inicio");
      }
    )
  }

  registrarse(){
    this.router.navigateByUrl("registro");
  }
}

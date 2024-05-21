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
    /*
    this.loginService.login(this.loginForm.value as LoginRequest).subscribe({
      next:(userData) =>{
        console.log(userData);
      },
      error:(errorData) =>{
        console.log(errorData);
      },
      complete:() =>{
        console.log("OKKK");
        //
        this.loginForm.reset();
      }
    });
    console.log(this.loginForm.value as LoginRequest);*/

    this.loginService.login2(this.loginForm.value as LoginRequest).subscribe(
      response => {
        sessionStorage.setItem("token",response.token);
        this.router.navigateByUrl("inicio");
      },
    )
  }
}

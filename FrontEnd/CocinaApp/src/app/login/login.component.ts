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
      username: ['', [Validators.required, Validators.minLength(3), this.validarUsuario]],
        password: ['', [Validators.required, Validators.minLength(5), this.validarPasswd]]
    });
  }

  validarFormulario():boolean{
    return this.loginForm.valid;
   
  }
  validarPasswd(control:any):{ [key:string]: boolean}| null {
    const contra = control.value;
    if (contra.length < 5) {
      return {'passwordLenght':true}
    }  
    const hasLowercase = /[a-z]/.test(contra);
    const hasUppercase = /[A-Z]/.test(contra);
    const hasDigit = /[0-9]/.test(contra);
  
    if (!hasLowercase || !hasUppercase || !hasDigit) {
      return {'passwordComplexity': true};
    }
    return null;
  }

  validarUsuario(control:any):{[key:string]:boolean} | null{
    const user = control.value;
    if(!user){
      return {'required':true};
    }
    const validCharacters = /^[a-zA-Z0-9_]+$/;
    if (!validCharacters.test(user)) {
      return {'invalidUsername':true};
    }
    return null;
  }

  login(){
    // if(this.validarFormulario()){
      this.loginService.login(this.loginForm.value as LoginRequest).subscribe(
        response => {
          this.router.navigateByUrl("inicio");
        },
        error => {
          this.showError = true;
        }
      )
    // }
    // else{
    //   this.showError = true;
    // }
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

import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { LoginService } from '../servicios/auth/login.service';
import { registroRequest } from '../servicios/auth/registroRequest';
import { response } from 'express';
import { error } from 'console';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.css'
})
export class RegistroComponent {

  registrarForm = this.formBuilder.group({
    username:[''],
    password:['']
  })

  constructor(private formBuilder:FormBuilder,private loginServicio:LoginService,private router:Router){
  }

  registro():void{
    this.loginServicio.registroUsuario(this.registrarForm.value as registroRequest).subscribe(
      {
        error : error =>{
          console.error("Error al registrar" + error);
        }
      }
    );
    this.router.navigateByUrl("/login");
  }

  login(){
    this.router.navigateByUrl("/login");

  }
}

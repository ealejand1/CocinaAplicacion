import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoginService } from '../servicios/auth/login.service';
import { registroRequest } from '../servicios/auth/registroRequest';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.css'
})
export class RegistroComponent implements OnInit {

  registrarForm:FormGroup;
  showError: boolean = false;

  constructor(private formBuilder:FormBuilder,private loginServicio:LoginService,private router:Router){
  }
  ngOnInit(): void {
    this.registrarForm = this.formBuilder.group({
      username:['',[Validators.required, Validators.minLength(3), this.validarUsuario]],
      password:['', [Validators.required, Validators.minLength(5), this.validarPasswd]]
    })
  }

  validarFormulario():boolean{
    return this.registrarForm.valid;
  }

  validarPasswd(control:any):{ [key:string]: boolean} | null {
    const contra = control.value;
    if (contra.length < 5) {
      return {'passwordLength':true}
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
  
  registro():void{
    this.loginServicio.registroUsuario(this.registrarForm.value as registroRequest).subscribe(
      {
        next:next =>{
          this.router.navigateByUrl("/login");
        },
        error:error => {
          this.showError = true;
        },
      }
    );
   
  }

  login(){
    this.router.navigateByUrl("/login");

  }
}

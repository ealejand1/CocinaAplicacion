import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  ruta: string='../assets/vegetables.jpg'
  
  loginForm = this.formBuilder.group({
    usuario:'',
    password:''
  })


  constructor(private formBuilder:FormBuilder){

  }
  
  ngOnInit(): void {
  }

}

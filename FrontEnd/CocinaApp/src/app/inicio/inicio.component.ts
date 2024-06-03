import { Component } from '@angular/core';
import { Categoria } from '../clases/categoria';
import { CategoriasService } from '../servicios/categorias.service';
import { UsuarioService } from '../servicios/auth/usuario.service';
import { response } from 'express';
import { error } from 'console';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.css'
})
export class InicioComponent {

  categorias:Categoria[];

  usuarioRol: string;
  
  usuarioID:any = localStorage.getItem("idUsuario");

  constructor(private categoriasServicio:CategoriasService,private usuarioServicio:UsuarioService){}

  ngOnInit(): void {
    this.obtenerCategorias();
    this.obtenerRoldeUsuario(this.usuarioID);
  }

  private obtenerCategorias(){
    this.categoriasServicio.obtenerListaCategorias().subscribe(categorias => {
      this.categorias = categorias;
    })
  }

  private obtenerRoldeUsuario(idUsuario:number){

    this.usuarioServicio.obtenerRolporIdUsuario(idUsuario).subscribe({
      next: rol => {
        this.usuarioServicio.cambiarRol(rol);
      },
      error: error =>{
        console.log(error);
      }
    }
    )
  }
  

}
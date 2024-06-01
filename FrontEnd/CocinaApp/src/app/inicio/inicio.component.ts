import { Component } from '@angular/core';
import { Categoria } from '../clases/categoria';
import { CategoriasService } from '../servicios/categorias.service';
import { UsuarioService } from '../servicios/auth/usuario.service';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.css'
})
export class InicioComponent {

  categorias:Categoria[];

  constructor(private categoriasServicio:CategoriasService,private usuarioServicio:UsuarioService){}

  ngOnInit(): void {
    this.obtenerCategorias();
  }

  private obtenerCategorias(){
    this.categoriasServicio.obtenerListaCategorias().subscribe(categorias => {
      this.categorias = categorias;
    })
  }

}

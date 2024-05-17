import { Component } from '@angular/core';
import { Categoria } from '../clases/categoria';
import { CategoriasService } from '../servicios/categorias.service';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrl: './inicio.component.css'
})
export class InicioComponent {

  categorias:Categoria[];

  constructor(private categoriasServicio:CategoriasService){}

  ngOnInit(): void {
    this.obtenerCategorias();
  }

  private obtenerCategorias(){
    this.categoriasServicio.obtenerListaCategorias().subscribe(categorias => {
      this.categorias = categorias;
    })
  }

}

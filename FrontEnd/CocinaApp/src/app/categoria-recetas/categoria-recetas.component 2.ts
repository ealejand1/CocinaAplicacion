import { Component } from '@angular/core';
import { Receta } from '../clases/receta';
import { RecetaService } from '../servicios/receta.service';
import { Router } from 'express';
import { ActivatedRoute } from '@angular/router';
import { CategoriasService } from '../servicios/categorias.service';
import { Categoria } from '../clases/categoria';

@Component({
  selector: 'app-categoria-recetas',
  templateUrl: './categoria-recetas.component.html',
  styleUrl: './categoria-recetas.component.css'
})
export class CategoriaRecetasComponent {
  recetas: Receta[];
  categoria: String;

  constructor(
    private recetaService: RecetaService,
    private categoriaService: CategoriasService,
    private route: ActivatedRoute,
    ) { }

  ngOnInit(): void {
    this.cargarRecetas();
    this.cargarCategoria();
  }

  cargarRecetas(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if(id){
      const categoriaId= +id;
      this.recetaService.obtenerRecetasPorCategoria(categoriaId).subscribe({
        next: (data: Receta[]) => {
          if (Array.isArray(data)) {
            this.recetas = data;
          } else {
            console.error('Datos recibidos no son un array:', data);
          }
        },
        error: error => console.error('Error al obtener las recetas:', error)
      });
    }
  }

  cargarCategoria(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if(id){
      const categoriaId= +id;
      this.categoriaService.obtenerCategoriaPorId(categoriaId).subscribe({
        next: (data: Categoria) => {
          this.categoria = data.nombre;
        },
        error: error => console.error('Error al obtener la categoria:', error)
      });
    }
  }
}

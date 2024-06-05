import { Component } from '@angular/core';
import { Receta } from '../clases/receta';
import { RecetaService } from '../servicios/receta.service';
import { Router } from 'express';
import { ActivatedRoute } from '@angular/router';
import { CategoriasService } from '../servicios/categorias.service';
import { Categoria } from '../clases/categoria';
import { ValoracionService } from '../servicios/valoracion.service';

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
    private valoracionService:ValoracionService
    ) { }

  ngOnInit(): void {
    this.cargarRecetas();
    this.cargarCategoria();
  }

  cargarRecetas(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      const categoriaId = +id;
      this.recetaService.obtenerRecetasPorCategoria(categoriaId).subscribe({
        next: (data: Receta[]) => {
          if (Array.isArray(data)) {
            this.recetas = data;
            this.recetas.forEach(receta => this.cargarValoraciones(receta));
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
  cargarValoraciones(receta: Receta): void {
    this.valoracionService.obtenerValoracionesPorReceta(receta.id).subscribe({
      next: valoraciones => {
        receta.valoraciones = valoraciones;
      },
      error: error => console.error('Error al obtener las valoraciones:', error)
    });
  }

  calcularPromedioValoracion(receta: Receta): number | null {
    if (receta.valoraciones && receta.valoraciones.length > 0) {
      const sumaTotal = receta.valoraciones.reduce((suma, valoracion) => suma + valoracion.puntuacion, 0);
      return sumaTotal / receta.valoraciones.length;
    }
    return null;  // Retornar null si no hay valoraciones para evitar división por cero
  }

  mostrarEstrellas(puntuacion: number | null): string {
    if (puntuacion === null) {
      return 'Sin valoraciones';  // Mensaje cuando no hay valoraciones
    }
    const redondeado = Math.round(puntuacion);  // Redondea al número entero más cercano
    return '★'.repeat(redondeado) + '☆'.repeat(5 - redondeado);  // Completa hasta 5 estrellas
  }
}

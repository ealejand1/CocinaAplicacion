import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Receta } from '../clases/receta';
import { RecetaService } from '../servicios/receta.service';
import { RecetaIngrediente } from "../clases/receta-ingrediente";
import { RecetaIngredienteService } from '../servicios/receta-ingrediente.service';
import { ValoracionService } from '../servicios/valoracion.service'; // Asegúrate de importar el servicio
import { Valoracion } from '../clases/valoracion'; // Asegúrate de importar el modelo de valoraciones si no lo has hecho

@Component({
  selector: 'app-receta-detalle',
  templateUrl: './receta-detalle.component.html',
  styleUrls: ['./receta-detalle.component.css']
})
export class RecetaDetalleComponent implements OnInit {
  receta: Receta;
  ingredientes: RecetaIngrediente[] = [];
  valoraciones: Valoracion[] = []; // Asegúrate de tener esta propiedad para almacenar las valoraciones

  constructor(
    private route: ActivatedRoute,
    private recetaService: RecetaService,
    private recetaIngredienteService: RecetaIngredienteService,
    private valoracionService: ValoracionService // Asegúrate de inyectar el servicio aquí
  ) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      const recetaId = +id;
      this.recetaService.obtenerRecetaPorId(recetaId).subscribe(receta => {
        this.receta = receta;
        this.cargarValoraciones(recetaId); 
      });
      this.recetaIngredienteService.obtenerIngredientesPorReceta(recetaId).subscribe(
        ingredientes => {
          console.log('Ingredientes cargados:', ingredientes);
          this.ingredientes = ingredientes || [];
        },
        error => {
          console.error('Error al cargar ingredientes:', error);
        }
      );
    } else {
      console.error('ID de receta no encontrado en la URL');
    }
  }

  private cargarValoraciones(recetaId: number): void {
    this.valoracionService.obtenerValoracionesPorReceta(recetaId).subscribe(
      valoraciones => {
        this.valoraciones = valoraciones;
      },
      error => {
        console.error('Error al cargar valoraciones:', error);
      }
    );
  }
  mostrarEstrellas(puntuacion: number | null): string {
    if (puntuacion === null) {
      return 'Sin valoraciones';  // Message when there are no ratings
    }
    const redondeado = Math.round(puntuacion);  // Round to the nearest whole number
    return '★'.repeat(redondeado) + '☆'.repeat(5 - redondeado);  // Fill up to 5 stars
  }
}
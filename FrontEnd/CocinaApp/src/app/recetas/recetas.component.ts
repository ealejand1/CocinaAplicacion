import { Component } from '@angular/core';
import { Receta } from '../clases/receta';
import { RecetaService } from '../servicios/receta.service';
import { Router } from '@angular/router'; // Correct Router import from Angular
import { ValoracionService } from '../servicios/valoracion.service';

@Component({
  selector: 'app-recetas',
  templateUrl: './recetas.component.html',
  styleUrls: ['./recetas.component.css'] // Ensure it's styleUrls in an array format
})
export class RecetasComponent {
  recetas: Receta[] = [];

  constructor(
    private recetaService: RecetaService,
    private valoracionService: ValoracionService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.cargarRecetasPorUsuario();
  }

  cargarRecetasPorUsuario(): void {
    const userId = localStorage.getItem("idUsuario"); // Getting user ID from local storage
    if (userId) {
      this.recetaService.obtenerRecetasPorUsuarioId(+userId).subscribe({
        next: (data: Receta[]) => {
          if (Array.isArray(data)) {
            this.recetas = data;
            this.recetas.forEach(receta => this.cargarValoraciones(receta)); // Load all ratings at once
          } else {
            console.error('Datos recibidos no son un array:', data);
          }
        },
        error: error => console.error('Error al obtener las recetas:', error)
      });
    }
  }

  cargarValoraciones(receta: Receta): void {
    this.valoracionService.obtenerValoracionesPorReceta(receta.id).subscribe({
      next: valoraciones => {
        receta.valoraciones = valoraciones; // Store ratings in each recipe object
      },
      error: error => console.error('Error al obtener las valoraciones:', error)
    });
  }

  calcularPromedioValoracion(receta: Receta): number | null {
    if (receta.valoraciones && receta.valoraciones.length > 0) {
      const sumaTotal = receta.valoraciones.reduce((suma, valoracion) => suma + valoracion.puntuacion, 0);
      return sumaTotal / receta.valoraciones.length;
    }
    return null;  // Return null if no ratings to avoid division by zero
  }

  mostrarEstrellas(puntuacion: number | null): string {
    if (puntuacion === null) {
      return 'Sin valoraciones';  // Message when there are no ratings
    }
    const redondeado = Math.round(puntuacion);  // Round to the nearest whole number
    return '★'.repeat(redondeado) + '☆'.repeat(5 - redondeado);  // Fill up to 5 stars
  }

  editarReceta(id: number): void {
    this.router.navigate(['/editar-receta', id]);
  }

  eliminarReceta(id: number): void {
    this.recetaService.eliminarReceta(id).subscribe({
      next: () => {
        console.log('Receta eliminada con éxito');
        this.cargarRecetasPorUsuario(); // Reload the list of recipes after deletion
      },
      error: error => console.error('Error al eliminar la receta:', error)
    });
  }
}

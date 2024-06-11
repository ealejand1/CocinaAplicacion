import { Component } from '@angular/core';
import { Receta } from '../clases/receta';
import { RecetaService } from '../servicios/receta.service';
import { ValoracionService } from '../servicios/valoracion.service';
import { Valoracion } from '../clases/valoracion';

@Component({
  selector: 'app-recetas',
  templateUrl: './recetas.component.html',
  styleUrl: './recetas.component.css'
})
export class RecetasComponent {
  recetas: Receta[]=[];
  valoraciones:Valoracion[]=[];
  userId:any;
  estrella: { [key: number]: string } = {};

  constructor(private recetaService: RecetaService, private valoracionServicio: ValoracionService) { }

  ngOnInit(): void {
    
      this.userId = Number(localStorage.getItem("idUsuario"));
      this.cargarRecetasPorUsuarioId(this.userId);
      console.log(this.recetas);
  }

  //ERROR AQUI XD
  cargarRecetasPorUsuarioId(userId: number): void {
    this.recetaService.obtenerRecetasPorUsuarioId(userId).subscribe({
      next: (data) => {
        if (Array.isArray(data)) {
          this.recetas = data;
          console.log('Recetas cargadas:', this.recetas);  // Aquí es donde los datos están disponibles
          this.recetas.forEach(receta => {
            this.estrella[receta.id]=this.mostrarEstrellas(this.calcularPromedioValoracion(receta))
            console.log(this.estrella[receta.id])
          })
        } else {
          console.error('Datos recibidos en cargarRecetasPorUsuarioId no son un array:', data);
        }
      },
      error: error => console.error('Error al obtener las recetas en cargarRecetasPorUsuarioId:', error)
    });
  }

eliminarReceta(id: number): void {
  this.recetaService.eliminarReceta(id).subscribe({
    next: () => {
      console.log('Receta eliminada con éxito');
      this.cargarRecetasPorUsuarioId(this.userId); // Recargar la lista de recetas después de eliminar
    },
    error: error => console.error('Error al eliminar la receta:', error)
  });
}

calcularPromedioValoracion(receta: Receta): number | null {
  this.valoracionServicio.obtenerValoracionesPorReceta(receta.id).subscribe(valoracion =>{
    this.valoraciones= valoracion;
  })
  if (this.valoraciones && this.valoraciones.length > 0) {
    const sumaTotal = this.valoraciones.reduce((suma, valoracion) => suma + valoracion.puntuacion, 0);
    return sumaTotal / this.valoraciones.length;
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

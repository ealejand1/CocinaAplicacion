import { Component } from '@angular/core';
import { Receta } from '../clases/receta';
import { RecetaService } from '../servicios/receta.service';

@Component({
  selector: 'app-recetas',
  templateUrl: './recetas.component.html',
  styleUrl: './recetas.component.css'
})
export class RecetasComponent {
  recetas: Receta[]=[];

  constructor(private recetaService: RecetaService) { }

  ngOnInit(): void {
    let userId:any  = localStorage.getItem("idUsuario");
    if (userId !== null && userId !== undefined){
      console.log(userId)
      
      this.cargarRecetasPorUsuarioId(+userId);
      
    }
   
  }

  cargarRecetasPorUsuarioId(userId: number): void {
    this.recetaService.obtenerRecetasPorUsuarioId(userId).subscribe({
      next: (data) => {
          // Asegúrate de que data es un array
        if (Array.isArray(data)) {
          this.recetas=data;
          console.error('Datos recibidos no son un array:', data);
        }
      },
      error: error => console.error('Error al obtener las recetas:', error)
    });
  }

 // En tu componente
cargarRecetas(): void {
  this.recetaService.obtenerRecetas().subscribe({
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
eliminarReceta(id: number): void {
  this.recetaService.eliminarReceta(id).subscribe({
    next: () => {
      console.log('Receta eliminada con éxito');
      this.cargarRecetas(); // Recargar la lista de recetas después de eliminar
    },
    error: error => console.error('Error al eliminar la receta:', error)
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

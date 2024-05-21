import { Component } from '@angular/core';
import { Receta } from '../clases/receta';
import { RecetaService } from '../servicios/receta.service';

@Component({
  selector: 'app-recetas',
  templateUrl: './recetas.component.html',
  styleUrl: './recetas.component.css'
})
export class RecetasComponent {
  recetas: Receta[];

  constructor(private recetaService: RecetaService) { }

  ngOnInit(): void {
    this.cargarRecetas();
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
}

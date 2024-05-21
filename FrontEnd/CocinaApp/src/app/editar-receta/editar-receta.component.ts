// editar-receta.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RecetaService } from '../servicios/receta.service';
import { Receta } from '../clases/receta';

@Component({
  selector: 'app-editar-receta',
  templateUrl: './editar-receta.component.html',
  styleUrls: ['./editar-receta.component.css']
})
export class EditarRecetaComponent implements OnInit {
  receta: Receta=new Receta();

  constructor(
    private recetaService: RecetaService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.recetaService.obtenerRecetaPorId(+id).subscribe(receta => {
        this.receta = receta;
      });
    }
  }

  onSubmit() {
    if (this.receta.id) { // Verificar si receta tiene ID para evitar enviar una receta vacía
      this.recetaService.actualizarReceta(this.receta.id, this.receta).subscribe({
        next: () => {
          this.router.navigate(['/recetas']);
        },
        error: (error) => {
          console.error('Error al actualizar la receta', error);
        }
      });
    }
  }
}

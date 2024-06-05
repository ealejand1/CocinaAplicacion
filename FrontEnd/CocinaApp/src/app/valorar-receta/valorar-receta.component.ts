import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Receta } from '../clases/receta';
import { Usuario } from '../clases/usuario';
import { Valoracion } from '../clases/valoracion';
import { ValoracionService } from '../servicios/valoracion.service';

@Component({
  selector: 'app-valorar-receta',
  templateUrl: './valorar-receta.component.html',
  styleUrl: './valorar-receta.component.css'
})
export class ValorarRecetaComponent implements OnInit {
  @Input() receta: Receta;
  valoracion: Valoracion = new Valoracion();
   // Asumiendo que el usuario está autenticado y se tiene su ID

  constructor(private valoracionService: ValoracionService, private router: Router) { }

  ngOnInit(): void {
    this.valoracion.usuario = new Usuario();
    this.valoracion.receta = this.receta;
    let userId:any  = localStorage.getItem("idUsuario");
    if (userId !== null && userId !== undefined){
      console.log("si hay usuario")
      this.receta.ingredientes = [];
      console.log(this.receta.ingredientes)
      this.valoracion.usuario.id =  userId ; // Usuario predefinido
      console.log(this.valoracion.usuario)
      this.receta.usuario={ id: userId}
    }
    else{
      console.log("no hay usuario")
    }
  }
  
  onSubmit(): void {
    this.valoracionService.crearValoracion(this.valoracion).subscribe({
      next: (response) => {
        console.log('Valoración creada con éxito', response);
        console.log(`recetas/${this.valoracion.receta.id}`);
        this.router.navigate([`/recetas/${this.valoracion.receta.id}`])
      },
      error: (error) => {
        console.error('Error al crear valoración', error);
      }
    });
  }
}

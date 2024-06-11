import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Valoracion } from '../../clases/valoracion';
import { ValoracionService } from '../valoracion.service';
import { error } from 'console';

@Injectable({
  providedIn: 'root'
})
export class ValoracionComunicadorService {

  private valoracionesSubject = new BehaviorSubject<Valoracion[]>([]);
  valoraciones$ :Observable<Valoracion[]> = this.valoracionesSubject.asObservable();

  constructor(private valoracionService:ValoracionService) { }

  cargarValoraciones(recetaId:number){
    this.valoracionService.obtenerValoracionesPorReceta(recetaId).subscribe(
      valoraciones => this.valoracionesSubject.next(valoraciones),
      error => console.error("Error al cargar Valoraciones",error),
    )
  }

  agregarValoracion(valoracion:Valoracion){
    console.log(valoracion);
    this.valoracionService.crearValoracion(valoracion).subscribe(
      nuevaValoracion =>{
        const valoraciones = [...this.valoracionesSubject.value,nuevaValoracion];
        this.valoracionesSubject.next(valoraciones);
      },
      error => console.error("Error al agregar valoracion",error),
    )
  }
}

import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Receta } from '../clases/receta';
import { Valoracion } from '../clases/valoracion';
import { ValoracionComunicadorService } from '../servicios/comunicadores/valoracion-comunicador.service';
import { LoginService } from '../servicios/auth/login.service';
import { map } from 'rxjs';

@Component({
  selector: 'app-valorar-receta',
  templateUrl: './valorar-receta.component.html',
  styleUrl: './valorar-receta.component.css'
})
export class ValorarRecetaComponent implements OnInit {
  @Input() receta: Receta;
  valoracion: Valoracion = new Valoracion();
  usuarioId = Number(localStorage.getItem("idUsuario"));

   // Asumiendo que el usuario está autenticado y se tiene su ID

  constructor(private valoracionComunicador: ValoracionComunicadorService, private loginService: LoginService) { }

  ngOnInit(): void {
  }
  
  onSubmit(): void {
    this.valoracion.receta_id = this.receta.id;
    this.valoracion.usuario_id = this.usuarioId;
    this.valoracionComunicador.agregarValoracion(this.valoracion);
    this.valoracion = new Valoracion;
  }
}

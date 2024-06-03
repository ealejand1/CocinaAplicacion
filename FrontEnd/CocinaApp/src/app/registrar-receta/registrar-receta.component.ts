import { Component, OnInit } from '@angular/core';
import { RecetaIngrediente } from '../clases/receta-ingrediente';
import { Receta } from '../clases/receta';
import { Ingrediente } from '../clases/ingrediente';
import { RecetaService } from '../servicios/receta.service';
import { IngredienteService } from '../servicios/ingrediente.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registrar-receta',
  templateUrl: './registrar-receta.component.html',
  styleUrls: ['./registrar-receta.component.css']
})
export class RegistrarRecetaComponent implements OnInit {
  receta: Receta = new Receta();
  todosLosIngredientes: Ingrediente[] = [];
  nuevoIngrediente: RecetaIngrediente = { id:0, cantidad: 0, unidades: '', ingrediente: new Ingrediente(), receta: new Receta };
  listaIngrediente: Ingrediente[]=[];
  constructor(
    private recetaService: RecetaService, 
    private ingredienteService: IngredienteService,
    private router: Router
  ) {}

  

  cargarIngredientes() {
    this.ingredienteService.obtenerIngredientes().subscribe(ingredientes => {
      this.todosLosIngredientes = ingredientes;
    });
  }

addIngredient() {
   
  }

  onSubmit() {
    this.recetaService.crearReceta(this.receta).subscribe({
      next: () => {
        console.log('Receta creada con éxito');
        this.router.navigate(['/recetas']);
      },
      error: (error) => console.error('Error al crear receta', error),
      complete:()=>{ console.log("funciona")}
    });
  }
  regresoRecetas(){
    this.router.navigate(["/recetas"])
  }
  ngOnInit() {
    console.log(localStorage.getItem("idUsuario") + "aaaaaaa")
    let userId:any  = localStorage.getItem("idUsuario");
    if (userId !== null && userId !== undefined){
      this.receta.ingredientes = [];
      this.receta.usuario = { id: userId }; // Usuario predefinido
      this.cargarIngredientes();
    }
    
  }
}
import { Component, OnInit } from '@angular/core';
import { RecetaIngrediente } from '../clases/receta-ingrediente';
import { Receta } from '../clases/receta';
import { Ingrediente } from '../clases/ingrediente';
import { RecetaService } from '../servicios/receta.service';
import { IngredienteService } from '../servicios/ingrediente.service';
import { Router } from '@angular/router';
import { RecetaIngredienteService } from '../servicios/receta-ingrediente.service';
import { Console } from 'console';

@Component({
  selector: 'app-registrar-receta',
  templateUrl: './registrar-receta.component.html',
  styleUrls: ['./registrar-receta.component.css']
})

export class RegistrarRecetaComponent implements OnInit {
  receta: Receta = new Receta();
  todosLosIngredientes: Ingrediente[];
  nuevoIngrediente: RecetaIngrediente = new RecetaIngrediente();  
  listaIngrediente: Ingrediente[];
  //FOTO
  imageFile: File;
  imageSrc: string | ArrayBuffer="";

  constructor(
    private recetaService: RecetaService, 
    private ingredienteService: IngredienteService,
    private recetaIngredienteService: RecetaIngredienteService,
    private router: Router
  ) {}


//funcion para agregar foto
  onFileSelected(event: Event): void {
    const target = event.target as HTMLInputElement;
    const file = target.files ? target.files[0] : null;
    if (file) {
        this.imageFile = file;
        const reader = new FileReader();
        reader.onload = (e) => {
            if (e.target && e.target.result) {
                this.imageSrc = e.target.result;
            }
        };
        reader.readAsDataURL(file);
    }
  }

  // cargamos la lista de ingredientes 
  cargarIngredientes() {
    this.ingredienteService.obtenerIngredientes().subscribe(ingredientes => {
      this.todosLosIngredientes = ingredientes;
    });
  }

  addIngredient() {
    if (this.nuevoIngrediente.ingrediente && this.nuevoIngrediente.cantidad > 0 && this.nuevoIngrediente.unidades) {
      this.receta.ingredientes.push({
        ...this.nuevoIngrediente,
        ingrediente: {...this.nuevoIngrediente.ingrediente} // Asegúrate de que este objeto está bien definido
      });
      console.log('Ingrediente Agregado:', this.nuevoIngrediente);
      this.nuevoIngrediente = new RecetaIngrediente();  // Reset para nueva entrada
    } else {
      console.error('Falta información del ingrediente');
    }
  }


  onSubmit() {
    if (this.imageFile) {
      // Crear receta con imagen
      this.recetaService.crearRecetaConImagen(this.receta, this.imageFile).subscribe({
        next: (recetaGuardada) => {
          console.log('Receta creada con éxito', recetaGuardada);
          this.guardarIngredientes(recetaGuardada.id);
      
        },
        error: (error) => {
          console.error('Error al crear receta con imagen', error);
  
        }
      });
    } else {
      // Crear receta sin imagen
    
      this.recetaService.crearReceta(this.receta).subscribe({
        next: (recetaGuardada) => {
          console.log('Receta creada con éxito', recetaGuardada)
          this.nuevoIngrediente.receta=recetaGuardada;
          this.guardarIngredientes(recetaGuardada.id);
          console.log(this.receta.ingredientes)
          
        },
        error: (error) => {
          console.error('Error al crear receta sin imagen', error);
          
        }
      });
    }
  }
  
  guardarIngredientes(recetaId: number) {
    this.receta.ingredientes.forEach(ing => {
      let nuevoIngrediente = {
        cantidad: ing.cantidad,
        unidades: ing.unidades,
        ingrediente: ing.ingrediente,  // Pasar el objeto completo de ingrediente
        receta: {id: recetaId } // Asociar el ID de la receta creada
      };
      
      this.recetaIngredienteService.crearRecetaIngrediente(nuevoIngrediente).subscribe({
        next: (ingredienteGuardado) => {
          console.log('Ingrediente guardado con éxito:', ingredienteGuardado);
        },
        error: (error) => {
          console.error('Error al guardar ingrediente', error);
        }
      });
    });
  }
  
  
  
  //regresamos a la lista de las recetas para ver la creada
  regresoRecetas(){
    this.router.navigate(["/recetas"])
  }

  //aqui iniciamos el localstorage
  //la lista de ingredientes de la receta vacia
  // cargamos los ingredientes todos
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

// editar-receta.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RecetaService } from '../servicios/receta.service';
import { Receta } from '../clases/receta';
import { RecetaIngrediente } from '../clases/receta-ingrediente';
import { Ingrediente } from '../clases/ingrediente';
import { IngredienteService } from '../servicios/ingrediente.service';
import { RecetaIngredienteService } from '../servicios/receta-ingrediente.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-editar-receta',
  templateUrl: './editar-receta.component.html',
  styleUrls: ['./editar-receta.component.css']
})
export class EditarRecetaComponent implements OnInit {
  receta: Receta=new Receta();
   todosLosIngredientes: Ingrediente[] = [];
  nuevoIngrediente: RecetaIngrediente = new RecetaIngrediente();
  imageSrc: string | ArrayBuffer="";

  imagenesPredeterminadas: string[] = [
    'assets/predeterminadas/img1.png',
    'assets/predeterminadas/img2.png',
    'assets/predeterminadas/img3.png',
    'assets/predeterminadas/img4.png',
    'assets/predeterminadas/img5.png',
    'assets/predeterminadas/img6.png',
  ]
  imagenSeleccionada: string = '';

  constructor(
    private recetaService: RecetaService,
    private ingredienteService: IngredienteService,
    private recetaIngredienteService: RecetaIngredienteService,
    private route: ActivatedRoute,
    private router: Router
  ) {  }
  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.recetaService.obtenerRecetaPorId(+id).subscribe(receta => {
        this.receta = receta;
        let userId = localStorage.getItem("idUsuario"); // Obtener el ID del usuario de localStorage
        if (userId) {
          this.receta.usuario = { id: userId };
          this.cargarIngredientes(receta.id);
          this.cargarTodosLosIngredientes();// Asignar el ID del usuario a la receta si existe
        }
      });
    }
  }

  onSubmit() {
    console.log("Imagen seleccionada antes de guardar:", this.receta.imagenUrl);
    this.receta.imagenUrl = this.imagenSeleccionada;
    if (this.receta.id) { // Verificar si receta tiene ID para evitar enviar una receta vacía
      console.log("Guardando receta con imagen:", this.receta.imagenUrl);
      console.log(this.receta.ingredientes)
      this.receta.ingredientes.forEach(ingrediente => {
        if (ingrediente.id) {
          this.guardarCambiosIngrediente(ingrediente);
        }
      });
      this.receta.imagenUrl = this.imagenSeleccionada;
      this.recetaService.actualizarReceta(this.receta.id, this.receta).subscribe({
        next: () => {
          Swal.fire({
            title: '¡Sazón al punto!',
            text: 'Los cambios en tu receta han sido añadidos con maestría. Está todo listo para deleitar paladares.',
            icon: 'success',
            iconColor: 'green',
            background: '#fcf4e3',
            confirmButtonColor: '#8B4513',
          
          });
                    this.router.navigate(['/recetas']);

        },
        error: (error) => {
          console.error('Error al actualizar la receta', error);
        }
      });
    }
  }
  addIngredient() {
    if (this.nuevoIngrediente.ingrediente && this.nuevoIngrediente.cantidad > 0 && this.nuevoIngrediente.unidades) {
      if (!this.receta.ingredientes) {
        this.receta.ingredientes = [];
      }
      this.receta.ingredientes.push({
        ...this.nuevoIngrediente,
        ingrediente: { ...this.nuevoIngrediente.ingrediente }
      });
      this.nuevoIngrediente = new RecetaIngrediente();  // Restablecer para nueva entrada
    } else {
      console.error('Falta información del ingrediente');
    }
  }

  
  removeIngredient(ingrediente: RecetaIngrediente) {
    this.receta.ingredientes = this.receta.ingredientes.filter(ing => ing !== ingrediente);
  }
  cargarIngredientes(recetaId: number) {
    this.recetaIngredienteService.obtenerIngredientesPorReceta(recetaId).subscribe({
      next: (ingredientes) => this.receta.ingredientes = ingredientes,
      error: (error) => console.error('Error al cargar ingredientes:', error)
    });
  }
  cargarTodosLosIngredientes() {
    this.ingredienteService.obtenerIngredientes().subscribe({
      next: (ingredientes) => this.todosLosIngredientes = ingredientes,
      error: (error) => console.error('Error al cargar todos los ingredientes:', error)
    });
  }
  guardarCambiosIngrediente(ingrediente: RecetaIngrediente): void {
    console.log(ingrediente);
    if (ingrediente.id) { // Solo actualiza si el ingrediente tiene un ID
      this.recetaIngredienteService.actualizarRecetaIngrediente(ingrediente.id, ingrediente).subscribe({
        next: (updatedIngredient) => {
          console.log('Ingrediente actualizado:', updatedIngredient);
          // Aquí podrías actualizar la lista de ingredientes en la vista si es necesario
        },
        error: (error) => {
          console.error('Error actualizando ingrediente', error);
        }
      });
    }
  }
  mostrarPopup: boolean = false;
  mostrarPopupImagenes() {
    this.mostrarPopup = true;
  }
  
  seleccionarImagen(imagen: string) {
    this.imagenSeleccionada = imagen;  // Guarda la URL de la imagen seleccionada
    this.imageSrc = imagen;            // Actualiza la fuente de la imagen para la previsualización
    this.mostrarPopup = false;         // Cierra el pop-up
  }
  
  
  cerrarPopup() {
    this.mostrarPopup = false;
  }
  
}

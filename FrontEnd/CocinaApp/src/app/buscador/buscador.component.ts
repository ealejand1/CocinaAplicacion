import { Component, OnInit } from '@angular/core';
import { Ingrediente } from '../clases/ingrediente';
import { IngredienteService } from '../servicios/ingrediente.service';
import { RecetaIngredienteService } from '../servicios/receta-ingrediente.service';
import { RecetaIngrediente } from '../clases/receta-ingrediente';
import { forkJoin, Observable } from 'rxjs';
import { Receta } from '../clases/receta';
import { RecetaService } from '../servicios/receta.service';

@Component({
  selector: 'app-buscador',
  templateUrl: './buscador.component.html',
  styleUrls: ['./buscador.component.css']
})
export class BuscadorComponent implements OnInit {
  ingredientes: Ingrediente[] = [];
  filteredIngredientes: Ingrediente[] = [];
  selectedIngredientes: Ingrediente[] = [];
  searchTerm: string = '';
  listaRecetas: Receta[] = [];
  isPorIngrediente: boolean= true;


  constructor(
    private ingredienteServicio: IngredienteService,
    private recetaIngredienteServicio: RecetaIngredienteService,
    private recetaServicio: RecetaService
    ) {}

  ngOnInit(): void {
    this.obtenerIngredientes();
    this.obtenerRecetas();
  }

  private obtenerIngredientes(): void {
    this.ingredienteServicio.obtenerIngredientes().subscribe(ingredientes => {
      this.ingredientes = ingredientes;
      this.filteredIngredientes = ingredientes; // Inicializar con todos los ingredientes
    });
  }

  private obtenerRecetas():void{
    this.recetaServicio.obtenerRecetas().subscribe(recetas =>{
      this.listaRecetas=recetas;
    })
  }

  filterIngredients(): void {
    const term = this.searchTerm.toLowerCase();
    this.filteredIngredientes = this.ingredientes.filter(ingrediente => ingrediente.nombre.toLowerCase().includes(term));
  }

  addIngredient(event: Event): void {
    event.preventDefault();
    const input = (event.target as HTMLFormElement).elements.namedItem('ingredientes') as HTMLInputElement;
    const ingredienteNombre = input.value;
    const ingrediente = this.ingredientes.find(ing => ing.nombre === ingredienteNombre);
    if (ingrediente && !this.selectedIngredientes.includes(ingrediente)) {
      this.selectedIngredientes.push(ingrediente);
    }
    input.value = ''; // Reset the input field
  }

  removeIngredient(ingrediente: Ingrediente): void {
    this.selectedIngredientes = this.selectedIngredientes.filter(ing => ing !== ingrediente);
  }

  buscarPorIngredientes(event: Event): void {
    event.preventDefault();
    this.listaRecetas = [];
    let recetasRecibidas: Receta[] = [];
    let ids: { [key: number]: number } = {}; //HasMap
    const ingredientesIds = this.selectedIngredientes.map(ing => ing.id);
    let listaRecetaIngrediente: Observable<RecetaIngrediente[]>[] = [];
    ingredientesIds.forEach(id => {
      listaRecetaIngrediente.push(this.recetaIngredienteServicio.obtenerRecetasPorIngrediente(id));
    });
  
    forkJoin(listaRecetaIngrediente).subscribe(resultados => {
      resultados.forEach(recetasIngrediente => {
        recetasIngrediente.forEach(recetaIngrediente => {
          if (isNaN(ids[recetaIngrediente.receta.id])) {
            recetasRecibidas.push(recetaIngrediente.receta);
            ids[recetaIngrediente.receta.id] = 1;
          } else {
            ids[recetaIngrediente.receta.id]++;
          }
        });
      });
  
      // Filtrar recetas que se han repetido el número de veces de ingredientes
      recetasRecibidas.forEach(receta => {
        if (ids[receta.id] === ingredientesIds.length) {
          this.listaRecetas.push(receta);
        }
      });
    });
  }

  buscarPorNombre(event: Event): void {
    console.log("OwO")
    event.preventDefault(); 
    const target = event.target as HTMLFormElement;
    const input = target.querySelector('input[name="nombreBuscar"]') as HTMLInputElement;
    const value = input.value;
    this.recetaServicio.obtenerRecetasPorNombre(value).subscribe(recetas =>{
      this.listaRecetas=recetas;
    })
  }

  cambiarBuscador(){
    if(this.isPorIngrediente){
      this.isPorIngrediente=false;
    }else{
      this.isPorIngrediente=true;
    }
  }
}

import { Categoria } from "./categoria";
import { RecetaIngrediente } from "./receta-ingrediente";
import { Usuario } from "./usuario";
import { Valoracion } from "./valoracion";

export class Receta {
    id: number;
    nombre: string;
    descripcion: string;
    instrucciones: string;
    tiempoPreparacion: number; 
    fechaCreacion: Date;
    usuario: Usuario;
    valoraciones: Valoracion[];
    categorias: Categoria[];
    ingredientes: RecetaIngrediente[];
    
}

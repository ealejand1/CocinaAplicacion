import { Ingrediente } from "./ingrediente";
import { Receta } from "./receta";

export class RecetaIngrediente {
    id?: number;
    cantidad: number;
    unidades: string;
    ingrediente: Ingrediente;
    receta:{ id: number };
} 

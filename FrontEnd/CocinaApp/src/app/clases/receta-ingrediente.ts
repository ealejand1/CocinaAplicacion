import { Ingrediente } from "./ingrediente";
import { Receta } from "./receta";

export class RecetaIngrediente {
    id?: number | null;
    cantidad: number;
    unidades: string;
    ingrediente: Ingrediente;
    receta:Receta;
} 

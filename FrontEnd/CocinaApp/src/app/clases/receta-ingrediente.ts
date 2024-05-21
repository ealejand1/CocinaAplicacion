import { Ingrediente } from "./ingrediente";

export class RecetaIngrediente {
    id?: number;
    cantidad: number;
    unidades: string;
    ingrediente: Ingrediente;
}
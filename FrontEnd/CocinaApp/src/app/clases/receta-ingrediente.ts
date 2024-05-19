import { Receta } from "./receta";

export class RecetaIngrediente {
    
        id: number;
        cantidad: number;
        unidades: string;
        receta: Receta;
        ingrediente: {
                id: number;
                nombre: string;
        };
            
}

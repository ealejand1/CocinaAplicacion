import { Receta } from "./receta";
import { Usuario } from "./usuario";

export class Valoracion {
    id: number;
    puntuacion: number;
    comentario: string;
    usuario: Usuario;
    receta:Receta;
}

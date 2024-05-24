package com.example.cocina.API.receta_ingrediente;

public class RecetaIngredienteNotFoundException extends RuntimeException{

	public RecetaIngredienteNotFoundException(Long id) {
		super("UnU ~ No se ha podido encontrar la relacion receta-igrediente con ID:"+id);
	}
}

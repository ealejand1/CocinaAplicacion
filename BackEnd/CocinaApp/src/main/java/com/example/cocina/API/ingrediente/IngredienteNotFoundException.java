package com.example.cocina.API.ingrediente;

public class IngredienteNotFoundException extends RuntimeException{

	public IngredienteNotFoundException(Long id) {
		super("UnU ~ No se ha podido encontrar el ingrediente con ID:"+id);
	}
}

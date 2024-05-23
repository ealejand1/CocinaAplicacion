package com.example.cocina.API.receta_ingrediente;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class RecetaIngredienteNotFoundAdvice {
	
	@ResponseBody //Indica que el valor devuelto se debe escribir en el cuerpo de la respuesta
	@ExceptionHandler(RecetaIngredienteNotFoundException.class) //Para manejar la excepción RecetaIngredienteNotFoundException cuando se lance
	@ResponseStatus(HttpStatus.NOT_FOUND) //Devuelve error 404 
	String recetaNotFoundHandler(RecetaIngredienteNotFoundException ex) {
		return ex.getMessage();
	}
}

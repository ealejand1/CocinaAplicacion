package com.example.cocina.API.categorias;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class CategoriaNotFoundAdvice {
	
	@ResponseBody //Indica que el valor devuelto se debe escribir en el cuerpo de la respuesta
	@ExceptionHandler(CategoriaNotFoundException.class) //Para manejar la excepción CategoriaNotFoundException cuando se lance
	@ResponseStatus(HttpStatus.NOT_FOUND) //Devuelve error 404 
	String recetaNotFoundHandler(CategoriaNotFoundException ex) {
		return ex.getMessage();
	}
}


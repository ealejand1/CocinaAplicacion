package app.receta;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import app.usuario.UsuarioNotFoundException;

@ControllerAdvice
@ResponseBody
public class RecetaNotFoundAdvice {
	
	@ResponseBody //Indica que el valor devuelto se debe escribir en el cuerpo de la respuesta
	@ExceptionHandler(RecetaNotFoundException.class) //Para manejar la excepción RecetaNotFoundException cuando se lance
	@ResponseStatus(HttpStatus.NOT_FOUND) //Devuelve error 404 
	String recetaNotFoundHandler(RecetaNotFoundException ex) {
		return ex.getMessage();
	}
}

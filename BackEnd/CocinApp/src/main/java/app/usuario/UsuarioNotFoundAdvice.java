package app.usuario;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class UsuarioNotFoundAdvice {

	@ResponseBody //Indica que el valor devuelto se debe escribir en el cuerpo de la respuesta
	@ExceptionHandler(UsuarioNotFoundException.class) //Para manejar la excepción UsuarioNotFoundException cuando se lance
	@ResponseStatus(HttpStatus.NOT_FOUND) //Devuelve error 404 
	String usuarioNotFoundHandler(UsuarioNotFoundException ex) {
		return ex.getMessage();
	}
}

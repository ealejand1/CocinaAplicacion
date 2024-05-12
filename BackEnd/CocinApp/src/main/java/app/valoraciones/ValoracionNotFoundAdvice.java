package app.valoraciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class ValoracionNotFoundAdvice {
	
	@ResponseBody //Indica que el valor devuelto se debe escribir en el cuerpo de la respuesta
	@ExceptionHandler(ValoracionNotFoundException.class) //Para manejar la excepción ValoracionNotFoundException cuando se lance
	@ResponseStatus(HttpStatus.NOT_FOUND) //Devuelve error 404 
	String usuarioNotFoundHandler(ValoracionNotFoundException ex) {
		return ex.getMessage();
	}

}

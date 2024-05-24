package com.example.cocina.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExpectionHandler {

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handlerArgException(IllegalArgumentException exp){
		return new ResponseEntity<String>(exp.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<String> handlerRuntimeException (RuntimeException exp){
		return new ResponseEntity<String>(exp.getMessage(),HttpStatus.BAD_GATEWAY);
	}
}

package com.example.cocina.user;

public class UsuarioNotFoundException extends RuntimeException{
	
	public UsuarioNotFoundException(Long id) {
		super("UnU ~ No se ha podido encontrar el usuario con ID:"+id);
	}

}

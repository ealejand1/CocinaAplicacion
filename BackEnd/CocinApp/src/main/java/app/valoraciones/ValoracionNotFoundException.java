package app.valoraciones;

public class ValoracionNotFoundException extends RuntimeException{
	
	public ValoracionNotFoundException(Long id) {
		super("UnU ~ No se ha podido encontrar la valoracion con ID:"+id);
	}

}

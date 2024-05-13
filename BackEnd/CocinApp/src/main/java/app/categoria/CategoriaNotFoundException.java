package app.categoria;

public class CategoriaNotFoundException extends RuntimeException{

	public CategoriaNotFoundException(Long id) {
		super("UnU ~ No se ha podido encontrar la categoria con ID:"+id);
	}
}
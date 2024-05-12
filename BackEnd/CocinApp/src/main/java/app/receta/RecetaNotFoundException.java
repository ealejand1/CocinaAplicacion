package app.receta;

public class RecetaNotFoundException extends RuntimeException{

	public RecetaNotFoundException(Long id) {
		super("UnU ~ No se ha podido encontrar la receta con ID:"+id);
	}
}

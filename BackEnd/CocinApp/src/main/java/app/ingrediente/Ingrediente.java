package app.ingrediente;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.receta_ingrediente.RecetaIngrediente;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Ingrediente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;
	
	protected String nombre;
	
	@ManyToMany(mappedBy = "ingrediente")
	@JsonIgnore
	protected List<RecetaIngrediente> recetas;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<RecetaIngrediente> getRecetas() {
		return recetas;
	}

	public void setRecetas(List<RecetaIngrediente> recetas) {
		this.recetas = recetas;
	}
	
	
}

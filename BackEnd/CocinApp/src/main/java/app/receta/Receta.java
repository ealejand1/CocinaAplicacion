package app.receta;

import java.time.Duration;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.categoria.Categoria;
import app.receta_ingrediente.RecetaIngrediente;
import app.usuario.Usuario;
import app.valoraciones.Valoracion;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Receta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;
	
	protected String nombre;
	protected String descripcion;
	protected String instrucciones;
	protected String tiempoPreparacion;
	
	@Temporal(TemporalType.TIMESTAMP)
    protected Date fechaCreacion;
	
	@ManyToOne // Relación muchos a uno con Usuario
    @JoinColumn(name = "usuario_id") // Nombre de la columna que hace referencia al usuario
    protected Usuario usuario;
	
	@OneToMany(mappedBy = "receta", cascade = CascadeType.ALL)
	@JsonIgnore
    protected List<Valoracion> valoraciones;
	
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    @JoinTable(
        name = "receta_categoria",
        joinColumns = @JoinColumn(name = "receta_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    protected List<Categoria> categorias;
    
    @OneToMany(mappedBy = "receta")
    @JsonIgnore
    protected List<RecetaIngrediente> ingredientes;
	
    // Configuración para generar la fecha de registro automáticamente antes de persistir
    @PrePersist
    protected void onCreate() {
    	fechaCreacion = new Date(); // Establecer la fecha actual antes de persistir
    }

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getInstrucciones() {
		return instrucciones;
	}

	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}

	public String getTiempoPreparacion() {
		return tiempoPreparacion;
	}

	public void setTiempoPreparacion(String tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Valoracion> getValoraciones() {
		return valoraciones;
	}

	public void setValoraciones(List<Valoracion> valoraciones) {
		this.valoraciones = valoraciones;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<RecetaIngrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<RecetaIngrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}
    
    
	
}

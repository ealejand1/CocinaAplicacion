package app.valoraciones;

import java.util.Date;

import app.receta.Receta;
import app.usuario.Usuario;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Valoracion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;
	
	protected int puntuacion;
	protected String comentario;
	
	@Temporal(TemporalType.TIMESTAMP)
    protected Date fechaCreacion;
	

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    protected Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "receta_id")
    protected Receta receta;
	
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

	public int getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
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

	public Receta getReceta() {
		return receta;
	}

	public void setReceta(Receta receta) {
		this.receta = receta;
	}
	
	
}

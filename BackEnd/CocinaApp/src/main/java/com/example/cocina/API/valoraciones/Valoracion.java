package com.example.cocina.API.valoraciones;

import java.util.Date;

import com.example.cocina.API.receta.Receta;
import com.example.cocina.user.User;

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
	protected Long id;
	
	protected int puntuacion;
	protected String comentario;
	
	@Temporal(TemporalType.TIMESTAMP)
    protected Date fechaCreacion;
	

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    protected User usuario;

    @ManyToOne
    @JoinColumn(name = "receta_id")
    protected Receta receta;
	
	@PrePersist
    protected void onCreate() {
		fechaCreacion = new Date(); // Establecer la fecha actual antes de persistir
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public Receta getReceta() {
		return receta;
	}

	public void setReceta(Receta receta) {
		this.receta = receta;
	}
	
	
}

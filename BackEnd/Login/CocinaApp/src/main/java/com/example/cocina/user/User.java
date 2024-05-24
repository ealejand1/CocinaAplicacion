package com.example.cocina.user;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.cocina.API.receta.Receta;
import com.example.cocina.API.valoraciones.Valoracion;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user",uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@Column(nullable = false)
	String username;
	String password;
	
	@Enumerated(EnumType.STRING)
	Role rol;
	
	@Temporal(TemporalType.TIMESTAMP)
    protected Date fechaCreacion;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL) // Relación uno a muchos con Receta
	@JsonIgnore
	protected List<Receta> recetas;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    protected List<Valoracion> valoraciones;
	
	// Configuración para generar la fecha de registro automáticamente antes de persistir
    @PrePersist
    protected void onCreate() {
    	fechaCreacion = new Date(); // Establecer la fecha actual antes de persistir
    }
	
	
	//Estos metodos sirve para poder trabajar con la autenticacion del USUARIO
	
	//Returna una Lista de objeto que nos dice el rol que tendra el usuario
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + (rol != null ? rol.name() : "USER")));
    }

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}

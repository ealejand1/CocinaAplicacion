package com.example.cocina.Invitado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cocina.user.UserRepositorio;
import com.example.cocina.user.Role;
import com.example.cocina.user.User;

import jakarta.annotation.PostConstruct;

@Service
public class InvitadoServicio {

	@Autowired
	private UserRepositorio userRepositorio;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostConstruct
	public void init() {
		if(userRepositorio.findByUsername("invitado").isEmpty()) {
			User invitado = User.builder()
					.username("invitado")
					.password(passwordEncoder.encode("invitado"))
					.rol(Role.INVITADO)
					.build();
			userRepositorio.save(invitado);
		}
	}
	
	public User getInvitadoUsuario() {
		return userRepositorio.findByUsername("invitado").orElseThrow();
	}
}

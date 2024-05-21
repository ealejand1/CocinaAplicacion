package com.example.cocina.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cocina.JWT.JwtServicio;
import com.example.cocina.user.Role;
import com.example.cocina.user.User;
import com.example.cocina.user.UserRepositorio;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	@Autowired
	private UserRepositorio userRepositorio;
	@Autowired
	private JwtServicio jwtServicio;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		UserDetails user = userRepositorio.findByUsername(request.getUsername()).orElseThrow();
		String token = jwtServicio.getToken(user);
		return AuthResponse.builder()
				.token(token)
				.build();
	}

	//Metodo que sirve para poder crear un usuario con el Lombok
	public AuthResponse register(RegistroRequest request) {
		User usuario = User.builder()
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.rol(Role.USER)
				.build();
		
		//Guardamos el Objeto Usuario en nuestra BBDD
		userRepositorio.save(usuario);
		
		//Obtenemos el token gracias al servicioJWT
		return AuthResponse.builder()
				.token(jwtServicio.getToken(usuario))
				.build();
				
	}

}

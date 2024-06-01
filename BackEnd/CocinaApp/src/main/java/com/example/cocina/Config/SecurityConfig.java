package com.example.cocina.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.cocina.JWT.jwtAuthFilter;

import lombok.RequiredArgsConstructor;

//Esta clase sirve para tener filtros de seguridad
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
	private jwtAuthFilter jwtauthFilter;
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	// Metodo que sirve para restringir acceso a las rutas
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf ->
				csrf
				.disable())
				
				.authorizeHttpRequests(
						authRequest -> authRequest
						.requestMatchers("/auth/**").permitAll()
						
						.requestMatchers(HttpMethod.GET, "/method/get").hasAnyRole("USER", "ADMIN","INVITADO")
		                .requestMatchers(HttpMethod.POST, "/method/post").hasAnyRole("USER", "ADMIN","INVITADO")
		                .requestMatchers(HttpMethod.DELETE, "/method/delete").hasAnyRole("USER", "ADMIN")
		                .requestMatchers(HttpMethod.PUT, "/method/put").hasAnyRole("USER", "ADMIN")
		                .anyRequest().authenticated()
						)
				
				.sessionManagement(sessionManager ->
					sessionManager
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtauthFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

}

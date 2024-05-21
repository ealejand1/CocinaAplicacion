package com.example.cocina.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.cocina.user.UserRepositorio;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
	
	@Autowired
	private UserRepositorio repositorio;
	
	@Bean
	public AuthenticationManager authenticationManager( AuthenticationConfiguration config) throws Exception {
		
		return config.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		
		authenticationProvider.setUserDetailsService(userDetailService());
		authenticationProvider.setPasswordEncoder(passwordEnconder());
		return authenticationProvider;
	}

	@Bean
	public PasswordEncoder passwordEnconder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDetailsService userDetailService() {
		return username -> repositorio.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("user not found"));
	}
}

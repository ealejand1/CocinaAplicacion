package com.example.cocina.JWT;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//La clase abstracta sirve para crear filtros personalizdos
//Tambien hace que el filtro se ejecute solo 1 vez por cada solicitud HTTP
@Component
public class jwtAuthFilter extends OncePerRequestFilter {

	@Autowired
	private JwtServicio jwtServicio;
	@Autowired
	private UserDetailsService userDetailsService;
	
	//Este el metodo/filtro que va a comprobar el JWT este bien formado.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String token = getTokenRequest(request);
		final String username;
		
		if(token == null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		username = jwtServicio.getUsernameFromToken(token);
		
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userdetails = userDetailsService.loadUserByUsername(username);
			
			if(jwtServicio.isTokenValid(token, userdetails)) {
				
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
		
	}

	//Metodo para obtener el JWT
	private String getTokenRequest(HttpServletRequest request) {
		
		final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
			
			return authHeader.substring(7);
		}
		return null;
	}

}

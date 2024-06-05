package com.example.cocina.JWT;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServicio {

	private static String SECRET_KEY = "12343342234323243222455243558587359873584359435";
	
	public String getToken(UserDetails usuario) {
		return getToken(new HashMap<>(), usuario);
	}

	//Esto metodo sirve para generar la firma de nuestro TOKEN
	private String getToken(Map<String, Object> extraClaim, UserDetails usuario) {
		return Jwts.builder()
				.setClaims(extraClaim)
				.setSubject(usuario.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
				.signWith(getKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	//Sirve para codificar el valor de la key que nos va a llegar del FRONT
	private Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String getUsernameFromToken(String token) {
		return getClaim(token, Claims ::getSubject);
	}

	public boolean isTokenValid(String token, UserDetails userdetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userdetails.getUsername()) && !isTokenExpired(token));
	}
	
	private Claims getAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public <T> T getClaim(String token, Function<Claims,T> claimsResolver){
		
		final Claims claim= getAllClaims(token);
		return claimsResolver.apply(claim);
	}
	private Date getExpiration(String token) {
		return getClaim(token, Claims ::getExpiration);
	}
	
	private boolean isTokenExpired(String token) {
		return getExpiration(token).before(new Date());
	}

}

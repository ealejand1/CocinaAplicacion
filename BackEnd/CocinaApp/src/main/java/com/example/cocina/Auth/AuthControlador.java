package com.example.cocina.Auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthControlador {
	
	@Autowired
	private AuthService authService;
	@PostMapping("login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request ) {
		
		return ResponseEntity.ok(authService.login(request));
	}
	
	@PostMapping("loginInvitado")
	public ResponseEntity<AuthResponse> loginInvitado() {
		
		return ResponseEntity.ok(authService.loginInvitado());
	}
	
	@PostMapping("registro")
	public ResponseEntity<AuthResponse> registro(@RequestBody RegistroRequest request ) {
		
		return ResponseEntity.ok(authService.register(request));
	}
	

}

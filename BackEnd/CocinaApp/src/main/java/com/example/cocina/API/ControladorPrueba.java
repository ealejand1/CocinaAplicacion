package com.example.cocina.API;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ControladorPrueba {
	
	@PostMapping("demo")
	public String demo() {
		return "hola";
	}

}

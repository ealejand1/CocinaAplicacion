package com.example.cocina.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/usuarios")
public class ControladorUsuarios {

	@Autowired
	private RepositorioUsuario repositorio;
	@Autowired
	private CreadorLinksUsuario creaLinks;


	// Obtener todos los usuarios (GET)
	@GetMapping
	public ResponseEntity<List<User>> obtenerUsuarios(){
		return ResponseEntity.ok(repositorio.findAll());
	}

	// Obtener un usuario por su ID (GET)
	@GetMapping("/{id}")
	public EntityModel<User> obtenerUsuarioPorId(@PathVariable("id") Long id) {
		User usuario = repositorio.findById(id).orElseThrow(() -> new UsuarioNotFoundException(id));
		return creaLinks.toModel(usuario);
	}

	//ME QUEDA OBTENER EL ROL DE UN USUARIO POR SU ID
	@GetMapping("/{id}/rol")
	public ResponseEntity<Role> obtenerRolporUsuarioId(@PathVariable("id") Long id){
		return ResponseEntity.ok(repositorio.findRolById(id));
	}
	
	
	// Eliminar un usuario por su ID (DELETE)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarUsuario(@PathVariable("id") Long id) {
		repositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}

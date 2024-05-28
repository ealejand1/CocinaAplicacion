package com.example.cocina.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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

	private final RepositorioUsuario repositorio;
	private final CreadorLinksUsuario creaLinks;

	public ControladorUsuarios(RepositorioUsuario repositorio, CreadorLinksUsuario creaLinks) {
		this.repositorio = repositorio;
		this.creaLinks = creaLinks;
	}

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

	// Crear un nuevo usuario (POST)
	@PostMapping
	public ResponseEntity<EntityModel<User>> crearUsuario(@RequestBody User usuario) {
		EntityModel<User> usuarioRes = creaLinks.toModel(repositorio.save(usuario));
		return ResponseEntity.created(usuarioRes.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(usuarioRes);
	}

	// Actualizar un usuario (PUT)
	@PutMapping("/{id}")
	public ResponseEntity<EntityModel<User>> actualizarUsuario(@PathVariable("id") Long id,
			@RequestBody User usuarioNuevo) {

		User usuarioActu = repositorio.findById(id).map(usuario -> {
			usuario.setUsername(usuarioNuevo.getUsername());
			// Añadir aquí más campos que quieras actualizar
			return repositorio.save(usuario);
		}).orElseGet(() -> {
			usuarioNuevo.setId(id);
			return repositorio.save(usuarioNuevo);
		});

		EntityModel<User> usuarioRes = creaLinks.toModel(usuarioActu);

		return ResponseEntity.created(usuarioRes.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(usuarioRes);
	}

	// Eliminar un usuario por su ID (DELETE)
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarUsuario(@PathVariable("id") Long id) {
		repositorio.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}

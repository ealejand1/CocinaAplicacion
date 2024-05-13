package app.usuario;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios") 
public class ControladorUsuarios {

    private final RepositorioUsuario repositorio; 
    private final CreadorLinksUsuario creaLinks;

    public ControladorUsuarios(RepositorioUsuario repositorio, CreadorLinksUsuario creaLinks) {
        this.repositorio = repositorio;
        this.creaLinks = creaLinks;
    }

    // Obtener todos los usuarios (GET)
    @GetMapping
    public CollectionModel<EntityModel<Usuario>> obtenerUsuarios() {

        List<EntityModel<Usuario>> usuarios = repositorio.findAll().stream()
                .map(creaLinks::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(usuarios,
                linkTo(methodOn(ControladorUsuarios.class).obtenerUsuarios())
                        .withSelfRel()
        );
    }

    // Obtener un usuario por su ID (GET)
    @GetMapping("/{id}")
    public EntityModel<Usuario> obtenerUsuarioPorId(@PathVariable("id") Long id) {
        Usuario usuario = repositorio.findById(id).orElseThrow(() -> new UsuarioNotFoundException(id));
        return creaLinks.toModel(usuario);
    }

    // Crear un nuevo usuario (POST)
    @PostMapping
    public ResponseEntity<EntityModel<Usuario>> crearUsuario(@RequestBody Usuario usuario) {
        EntityModel<Usuario> usuarioRes = creaLinks.toModel(repositorio.save(usuario));
        return ResponseEntity
                .created(usuarioRes.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(usuarioRes);
    }

    // Actualizar un usuario (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Usuario>> actualizarUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuarioNuevo) {

        Usuario usuarioActu = repositorio.findById(id)
                .map(usuario -> {
                    usuario.setNombre(usuarioNuevo.getNombre());
                    // Añadir aquí más campos que quieras actualizar
                    return repositorio.save(usuario);
                })
                .orElseGet(() -> {
                    usuarioNuevo.setId(id);
                    return repositorio.save(usuarioNuevo);
                });

        EntityModel<Usuario> usuarioRes = creaLinks.toModel(usuarioActu);

        return ResponseEntity
                .created(usuarioRes.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(usuarioRes);
    }
    
    //Modificar parcialmente un Usuario
  	@PatchMapping("/{id}")
  	public ResponseEntity<Usuario> actualizarParcialUsuario(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
          // Verificar si la raza con el ID dado existe antes de actualizar
          Optional<Usuario> usuarioExistente = repositorio.findById(id);
          
          if (usuarioExistente.isPresent()) {
              Usuario usuarioActual = usuarioExistente.get();
              
              // Actualizar solo los campos no nulos proporcionados en la solicitud
              if (usuario.getNombre() != null) {
            	  usuarioActual.setNombre(usuario.getNombre());
              }
              if (usuario.getContraseña() != null) {
            	  usuarioActual.setContraseña(usuario.getContraseña());
              }
              if (usuario.getEmail() != null) {
            	  usuarioActual.setEmail(usuario.getEmail());
              }           

              // Guardar el usuario actualizada en la base de datos
              Usuario usuarioActualizada = repositorio.save(usuarioActual);

              return ResponseEntity.ok(usuarioActual);
          } else {
              // Manejar el caso en el que el usuario no existe
              // Puedes devolver un error 404 o realizar alguna acción adecuada
              return ResponseEntity.notFound().build();
          }
      }		

    // Eliminar un usuario por su ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable("id") Long id) {
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


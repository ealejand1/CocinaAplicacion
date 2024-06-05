	package com.example.cocina.API.valoraciones;
	
	
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
	import org.springframework.web.bind.annotation.PatchMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.PutMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;
	
	@CrossOrigin(origins = "*")
	@RestController
	@RequestMapping("/api/v1/valoraciones") 
	public class ControladorValoraciones {
	
	    private final RepositorioValoracion repositorioValoracion; 
	    private final CreadorLinksValoracion creaLinksValoracion;
	
	    public ControladorValoraciones(RepositorioValoracion repositorioValoracion, CreadorLinksValoracion creaLinksValoracion) {
	        this.repositorioValoracion = repositorioValoracion;
	        this.creaLinksValoracion = creaLinksValoracion;
	    }
	    
	    // Obtener todas las valoraciones (GET)
	    @GetMapping
	    public CollectionModel<EntityModel<Valoracion>> obtenerValoraciones() {
	
	        List<EntityModel<Valoracion>> valoraciones = repositorioValoracion.findAll().stream()
	                .map(creaLinksValoracion::toModel)
	                .collect(Collectors.toList());
	
	        return CollectionModel.of(valoraciones,
	                linkTo(methodOn(ControladorValoraciones.class).obtenerValoraciones())
	                        .withSelfRel()
	        );
	    }
	
	    // Obtener una valoracion por su ID (GET)
	    @GetMapping("/{id}")
	    public EntityModel<Valoracion> obtenerValoracionPorId(@PathVariable("id") Long id) {
	        Valoracion valoracion = repositorioValoracion.findById(id).orElseThrow(() -> new ValoracionNotFoundException(id));
	        return creaLinksValoracion.toModel(valoracion);
	    }
	
	    // Obtener valoraciones por ID de Receta (GET)
	    @GetMapping("/{idReceta}/valoraciones")
	    public List<Valoracion> obtenerValoracionesPorReceta(@PathVariable("idReceta") Long idReceta) {
	        return repositorioValoracion.findByRecetaId(idReceta);
	    }
	
	    // Obtener valoraciones por ID de Usuario (GET)
	    @GetMapping("/usuarios/{idUsuario}")
	    public CollectionModel<EntityModel<Valoracion>> obtenerValoracionesPorUsuario(@PathVariable("idUsuario") Long idUsuario) {
	        List<EntityModel<Valoracion>> valoraciones = repositorioValoracion.findByUsuarioId(idUsuario).stream()
	                .map(creaLinksValoracion::toModel)
	                .collect(Collectors.toList());
	
	        return CollectionModel.of(valoraciones,
	                linkTo(methodOn(ControladorValoraciones.class).obtenerValoracionesPorUsuario(idUsuario)).withSelfRel()
	        );
	    }
	    
	    // Crear una nueva valoracion (POST)
	    @PostMapping
	    public ResponseEntity<EntityModel<Valoracion>> crearValoracion(@RequestBody Valoracion valoracion) {
	        EntityModel<Valoracion> valoracionRes = creaLinksValoracion.toModel(repositorioValoracion.save(valoracion));
	        return ResponseEntity
	                .created(valoracionRes.getRequiredLink(IanaLinkRelations.SELF).toUri())
	                .body(valoracionRes);
	    }
	
	    // Actualizar una valoracion (PUT)
	    @PutMapping("/{id}")
	    public ResponseEntity<EntityModel<Valoracion>> actualizarValoracion(@PathVariable("id") Long id, @RequestBody Valoracion valoracionNueva) {
	
	        Valoracion valoracionActu = repositorioValoracion.findById(id)
	                .map(valoracion -> {
	                    valoracion.setPuntuacion(valoracionNueva.getPuntuacion());
	                    valoracion.setComentario(valoracionNueva.getComentario());
	                    // Añadir aquí más campos que quieras actualizar
	                    return repositorioValoracion.save(valoracion);
	                })
	                .orElseGet(() -> {
	                    valoracionNueva.setId(id);
	                    return repositorioValoracion.save(valoracionNueva);
	                });
	
	        EntityModel<Valoracion> valoracionRes = creaLinksValoracion.toModel(valoracionActu);
	
	        return ResponseEntity
	                .created(valoracionRes.getRequiredLink(IanaLinkRelations.SELF).toUri())
	                .body(valoracionRes);
	    }
	
	    // Modificar parcialmente una Valoracion
	    @PatchMapping("/{id}")
	    public ResponseEntity<Valoracion> actualizarParcialValoracion(@PathVariable("id") Long id, @RequestBody Valoracion valoracion) {
	        Optional<Valoracion> valoracionExistente = repositorioValoracion.findById(id);
	        
	        if (valoracionExistente.isPresent()) {
	            Valoracion valoracionActual = valoracionExistente.get();
	            
	            // Actualizar solo los campos no nulos proporcionados en la solicitud
	            if (valoracion.getPuntuacion() != 0) {
	                valoracionActual.setPuntuacion(valoracion.getPuntuacion());
	            }
	            if (valoracion.getComentario() != null) {
	                valoracionActual.setComentario(valoracion.getComentario());
	            }
	
	            Valoracion valoracionActualizada = repositorioValoracion.save(valoracionActual);
	
	            return ResponseEntity.ok(valoracionActualizada);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }        
	
	    // Eliminar una valoracion por su ID (DELETE)
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminarValoracion(@PathVariable("id") Long id) {
	        repositorioValoracion.deleteById(id);
	        return ResponseEntity.noContent().build();
	    }
	}

package app.receta;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/recetas") 
public class ControladorRecetas {

    private final RepositorioReceta repositorio;
    private final CreadorLinksReceta creaLinks;

    public ControladorRecetas(RepositorioReceta repositorio, CreadorLinksReceta creaLinks) {
        this.repositorio = repositorio;
        this.creaLinks = creaLinks;
    }

    // Obtener todas las recetas (GET)
    @GetMapping
    public List<Receta> obtenerRecetas() {
        return repositorio.findAll().stream().collect(Collectors.toList());
    }

    // Obtener una receta por su ID (GET)
    @GetMapping("/{id}")
    public EntityModel<Receta> obtenerRecetaPorId(@PathVariable("id") Long id) {
        Receta receta = repositorio.findById(id).orElseThrow(() -> new RecetaNotFoundException(id));
        return creaLinks.toModel(receta);
    }

    @GetMapping("/usuario/{idUsuario}/recetas")	
    public CollectionModel<EntityModel<Receta>> obtenerRecetasPorUsuario(@PathVariable("idUsuario") Long idUsuario) {
        List<EntityModel<Receta>> recetas = repositorio.findByUsuarioId(idUsuario).stream()
                .map(creaLinks::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(recetas,
                linkTo(methodOn(ControladorRecetas.class).obtenerRecetasPorUsuario(idUsuario))
                        .withSelfRel()
        );
    }
    
    @GetMapping("/categoria/{idCategoria}/recetas")
    public ResponseEntity<List<Receta>> obtenerRecetasPorCategoria(@PathVariable("idCategoria") Long idCategoria) {
    	List<Receta> recetas = repositorio.findByCategoriaId(idCategoria);
    	return ResponseEntity.ok(recetas);
    	
//        List<EntityModel<Receta>> recetas = repositorio.findByCategoriaId(idCategoria).stream()
//                .map(creaLinks::toModel)
//                .collect(Collectors.toList());
//
//        return CollectionModel.of(recetas,
//                linkTo(methodOn(ControladorRecetas.class).obtenerRecetasPorCategoria(idCategoria))
//                .withSelfRel()
//        );
    }



    // Crear una nueva receta (POST)
    @PostMapping
    public ResponseEntity<EntityModel<Receta>> crearReceta(@RequestBody Receta receta) {
        EntityModel<Receta> recetaRes = creaLinks.toModel(repositorio.save(receta));
        return ResponseEntity
                .created(recetaRes.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(recetaRes);
    }

    // Actualizar una receta (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Receta>> actualizarReceta(@PathVariable("id") Long id, @RequestBody Receta recetaNueva) {

        Receta recetaActu = repositorio.findById(id)
                .map(receta -> {
                    receta.setNombre(recetaNueva.getNombre());
                    // Agrega aquí más campos que quieras actualizar
                    return repositorio.save(receta);
                })
                .orElseGet(() -> {
                    recetaNueva.setId(id);
                    return repositorio.save(recetaNueva);
                });

        EntityModel<Receta> recetaRes = creaLinks.toModel(recetaActu);

        return ResponseEntity
                .created(recetaRes.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(recetaRes);
    }
    
    // Modificar parcialmente una receta
    @PatchMapping("/{id}")
    public ResponseEntity<Receta> actualizarParcialReceta(@PathVariable("id") Long id, @RequestBody Receta receta) {
        Optional<Receta> recetaExistente = repositorio.findById(id);
        
        if (recetaExistente.isPresent()) {
            Receta recetaActual = recetaExistente.get();
            
            if (receta.getNombre() != null) {
                recetaActual.setNombre(receta.getNombre());
            }
            if (receta.getDescripcion() != null) {
                recetaActual.setDescripcion(receta.getDescripcion());
            }
            if (receta.getInstrucciones() != null) {
                recetaActual.setInstrucciones(receta.getInstrucciones());
            }
            if (receta.getTiempoPreparacion() != null) {
                recetaActual.setTiempoPreparacion(receta.getTiempoPreparacion());
            }

            Receta recetaActualizada = repositorio.save(recetaActual);

            return ResponseEntity.ok(recetaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Eliminar una receta por su ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReceta(@PathVariable("id") Long id) {
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

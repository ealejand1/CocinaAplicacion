package app.ingrediente;

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
@RequestMapping("/ingredientes") 
public class ControladorIngredientes {

    private final RepositorioIngrediente repositorio;
    private final CreadorLinksIngrediente creaLinks;

    public ControladorIngredientes(RepositorioIngrediente repositorio, CreadorLinksIngrediente creaLinks) {
        this.repositorio = repositorio;
        this.creaLinks = creaLinks;
    }

    // Obtener todos los ingredientes (GET)
    @GetMapping
    public CollectionModel<EntityModel<Ingrediente>> obtenerIngredientes() {

        List<EntityModel<Ingrediente>> ingredientes = repositorio.findAll().stream()
                .map(creaLinks::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(ingredientes,
                linkTo(methodOn(ControladorIngredientes.class).obtenerIngredientes())
                        .withSelfRel()
        );
    }

    // Obtener un ingrediente por su ID (GET)
    @GetMapping("/{id}")
    public EntityModel<Ingrediente> obtenerIngredientePorId(@PathVariable("id") Long id) {
        Ingrediente ingrediente = repositorio.findById(id).orElseThrow(() -> new IngredienteNotFoundException(id));
        return creaLinks.toModel(ingrediente);
    }

    // Crear un nuevo ingrediente (POST)
    @PostMapping
    public ResponseEntity<EntityModel<Ingrediente>> crearIngrediente(@RequestBody Ingrediente ingrediente) {
        EntityModel<Ingrediente> ingredienteRes = creaLinks.toModel(repositorio.save(ingrediente));
        return ResponseEntity
                .created(ingredienteRes.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(ingredienteRes);
    }

    // Actualizar un ingrediente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Ingrediente>> actualizarIngrediente(@PathVariable("id") Long id, @RequestBody Ingrediente ingredienteNuevo) {

        Ingrediente ingredienteActu = repositorio.findById(id)
                .map(ingrediente -> {
                    ingrediente.setNombre(ingredienteNuevo.getNombre());
                    // Agrega aquí más campos que quieras actualizar
                    return repositorio.save(ingrediente);
                })
                .orElseGet(() -> {
                    ingredienteNuevo.setId(id);
                    return repositorio.save(ingredienteNuevo);
                });

        EntityModel<Ingrediente> ingredienteRes = creaLinks.toModel(ingredienteActu);

        return ResponseEntity
                .created(ingredienteRes.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(ingredienteRes);
    }

    // Modificar parcialmente un ingrediente
    @PatchMapping("/{id}")
    public ResponseEntity<Ingrediente> actualizarParcialIngrediente(@PathVariable("id") Long id, @RequestBody Ingrediente ingrediente) {
        Optional<Ingrediente> ingredienteExistente = repositorio.findById(id);
        
        if (ingredienteExistente.isPresent()) {
            Ingrediente ingredienteActual = ingredienteExistente.get();
            
            if (ingrediente.getNombre() != null) {
                ingredienteActual.setNombre(ingrediente.getNombre());
            }
            // Agrega aquí más campos que quieras actualizar parcialmente

            Ingrediente ingredienteActualizado = repositorio.save(ingredienteActual);

            return ResponseEntity.ok(ingredienteActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un ingrediente por su ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarIngrediente(@PathVariable("id") Long id) {
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

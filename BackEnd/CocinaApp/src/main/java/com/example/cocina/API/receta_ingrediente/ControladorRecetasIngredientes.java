package com.example.cocina.API.receta_ingrediente;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cocina.API.receta.ControladorRecetas;
import com.example.cocina.API.receta.Receta;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/recetas-ingredientes") 
public class ControladorRecetasIngredientes {

    private final RepositorioRecetaIngrediente repositorio;
    private final CreadorLinksRecetaIngrediente creaLinks;

    public ControladorRecetasIngredientes(RepositorioRecetaIngrediente repositorio, CreadorLinksRecetaIngrediente creaLinks) {
        this.repositorio = repositorio;
        this.creaLinks = creaLinks;
    }

    // Obtener todos los ingredientes de recetas (GET)
    @GetMapping
    public CollectionModel<EntityModel<RecetaIngrediente>> obtenerRecetasIngredientes() {

        List<EntityModel<RecetaIngrediente>> recetasIngredientes = repositorio.findAll().stream()
                .map(creaLinks::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(recetasIngredientes,
                linkTo(methodOn(ControladorRecetasIngredientes.class).obtenerRecetasIngredientes())
                        .withSelfRel()
        );
    }

    // Obtener un ingrediente de receta por su ID (GET)
    @GetMapping("/{id}")
    public EntityModel<RecetaIngrediente> obtenerRecetaIngredientePorId(@PathVariable("id") Long id) {
        RecetaIngrediente recetaIngrediente = repositorio.findById(id).orElseThrow(() -> new RecetaIngredienteNotFoundException(id));
        return creaLinks.toModel(recetaIngrediente);
    }
    
    // Obtener los ingredientes de una receta por su ID (GET)
    @GetMapping("/receta/{receta_id}")
    public ResponseEntity<List<RecetaIngrediente>> obtenerIngredientesPorReceta(@PathVariable("receta_id") Long recetaId) {
        List<RecetaIngrediente> ingredientes = repositorio.findByRecetaId(recetaId);
        if (ingredientes.isEmpty()) {
            return ResponseEntity.noContent().build();  // Devuelve 204 No Content si la lista está vacía
        }
        return ResponseEntity.ok(ingredientes);  // Devuelve 200 OK con la lista de ingredientes
    }

    

    // Obtener las recetas de un ingrediente por su ID (GET)
    @GetMapping("/ingrediente/{ingrediente_id}")
    public ResponseEntity<List<RecetaIngrediente>> obtenerRecetasPorIngrediente(@PathVariable("ingrediente_id") Long ingredienteId) {
        List<RecetaIngrediente> recetasIngredientes = repositorio.findByIngredienteId(ingredienteId);

        return ResponseEntity.ok(recetasIngredientes);
    }
    
 // Buscar recetas por múltiples ingredientes (GET)
    @GetMapping("/recetas/{ingredientesIds}")
    public ResponseEntity<List<Long>> buscarRecetasPorIngredientes(@PathVariable("ingredientesIds") String ids) {
        // Dividir el string por comas
        String[] items = ids.split(",");

        // Crear una lista para almacenar los números
        List<Long> ingredientesIds = new ArrayList<>();

        // Convertir cada elemento del array de strings a Long y agregarlo a la lista
        for (String item : items) {
            try {
                ingredientesIds.add(Long.parseLong(item.trim()));
            } catch (NumberFormatException e) {
                // Manejar el caso en que el string no se puede convertir a Long
                System.err.println("Error al convertir el elemento a Long: " + item);
            }
        }

        // Validar la lista de IDs
        if (ingredientesIds.isEmpty()) {
            return ResponseEntity.badRequest().build();  // Devuelve 400 Bad Request si no se proporcionan IDs de ingredientes
        }

        // Obtén todas las recetas que contienen los ingredientes proporcionados
        List<Set<Long>> idRecetas = ingredientesIds.stream()
            .map(id -> repositorio.findByIngredienteId(id).stream()
                .map(recetaIngrediente -> recetaIngrediente.getReceta().getId())
                .collect(Collectors.toSet()))
            .collect(Collectors.toList());

        // Validar que no esté vacío
        if (idRecetas.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve 204 No Content si no hay recetas encontradas
        }

        // Encuentra las recetas que contienen todos los ingredientes
        Set<Long> recetasComunes = new HashSet<>(idRecetas.get(0));
        idRecetas.forEach(recetasComunes::retainAll);

        if (recetasComunes.isEmpty()) {
            return ResponseEntity.noContent().build();  // Devuelve 204 No Content si no hay recetas comunes
        }

        return ResponseEntity.ok(new ArrayList<>(recetasComunes));  // Devuelve 200 OK con la lista de recetas comunes
    }

    // Crear un nuevo ingrediente de receta (POST)
    @PostMapping
    public ResponseEntity<EntityModel<RecetaIngrediente>> crearRecetaIngrediente(@RequestBody RecetaIngrediente recetaIngrediente) {
        EntityModel<RecetaIngrediente> recetaIngredienteRes = creaLinks.toModel(repositorio.save(recetaIngrediente));
        return ResponseEntity
                .created(recetaIngredienteRes.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(recetaIngredienteRes);
    }

    // Actualizar un ingrediente de receta (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<RecetaIngrediente>> actualizarRecetaIngrediente(@PathVariable("id") Long id, @RequestBody RecetaIngrediente recetaIngredienteNuevo) {

        RecetaIngrediente recetaIngredienteActu = repositorio.findById(id)
                .map(recetaIngrediente -> {
                    recetaIngrediente.setCantidad(recetaIngredienteNuevo.getCantidad());
                    recetaIngrediente.setUnidades(recetaIngredienteNuevo.getUnidades());
                    // Agrega aquí más campos que quieras actualizar
                    return repositorio.save(recetaIngrediente);
                })
                .orElseGet(() -> {
                    recetaIngredienteNuevo.setId(id);
                    return repositorio.save(recetaIngredienteNuevo);
                });

        EntityModel<RecetaIngrediente> recetaIngredienteRes = creaLinks.toModel(recetaIngredienteActu);

        return ResponseEntity
                .created(recetaIngredienteRes.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(recetaIngredienteRes);
    }

    // Modificar parcialmente un ingrediente de receta
    @PatchMapping("/{id}")
    public ResponseEntity<RecetaIngrediente> actualizarParcialRecetaIngrediente(@PathVariable("id") Long id, @RequestBody RecetaIngrediente recetaIngrediente) {
        Optional<RecetaIngrediente> recetaIngredienteExistente = repositorio.findById(id);
        
        if (recetaIngredienteExistente.isPresent()) {
            RecetaIngrediente recetaIngredienteActual = recetaIngredienteExistente.get();
            
            if (recetaIngrediente.getCantidad() != 0) {
                recetaIngredienteActual.setCantidad(recetaIngrediente.getCantidad());
            }
            if (recetaIngrediente.getUnidades() != null) {
                recetaIngredienteActual.setUnidades(recetaIngrediente.getUnidades());
            }
            // Agrega aquí más campos que quieras actualizar parcialmente

            RecetaIngrediente recetaIngredienteActualizado = repositorio.save(recetaIngredienteActual);

            return ResponseEntity.ok(recetaIngredienteActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un ingrediente de receta por su ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRecetaIngrediente(@PathVariable("id") Long id) {
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


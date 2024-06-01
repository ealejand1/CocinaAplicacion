package com.example.cocina.API.receta;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "http://localhost:4200", methods = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET,
		RequestMethod.DELETE },allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/recetas")
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
    
    // Obtener receta por nombre (GET)
    @GetMapping("/nombre/{nombreReceta}")
    public ResponseEntity<List<Receta>> obtenerRecetasPorNombre(@PathVariable("nombreReceta") String nombreReceta){
    	List<Receta> recetas= repositorio.findByNombreParcial(nombreReceta);
    	return ResponseEntity.ok(recetas);
    }

    @GetMapping("/usuario/{idUsuario}/recetas")	
    public List<Receta> obtenerRecetasPorUsuario(@PathVariable("idUsuario") Long idUsuario) {
        return repositorio.findByUsuarioId(idUsuario);
                

        
       
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
                    receta.setCategorias(recetaNueva.getCategorias());
                    receta.setDescripcion(recetaNueva.getDescripcion());
                    receta.setTiempoPreparacion(recetaNueva.getTiempoPreparacion());
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
    @PostMapping("/conImagen")
    public ResponseEntity<EntityModel<Receta>> crearRecetaConImagen(@RequestParam("receta") String recetaJson, @RequestParam("file") MultipartFile file) {
        try {
            // Convertir JSON a objeto Receta, considerando incluir una librería como Jackson para hacerlo
            Receta receta = new ObjectMapper().readValue(	recetaJson, Receta.class);
            String filename = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            Path path = Paths.get("src/main/resources/static/uploads/" + filename);
            file.transferTo(path);
            receta.setImagenUrl("/uploads/" + filename); // Asumiendo que Receta tiene un campo para la URL de la imagen
            Receta recetaGuardada = repositorio.save(receta);
            EntityModel<Receta> recetaRes = creaLinks.toModel(recetaGuardada);
            return ResponseEntity.created(recetaRes.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(recetaRes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

package com.example.cocina.API.categorias;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cocina.API.receta.*;

import jakarta.transaction.Transactional;


@RestController
@RequestMapping("/api/v1/categorias")
@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET,
		RequestMethod.DELETE },allowedHeaders = "*")
public class ControladorCategorias {

    private final RepositorioCategoria repositorio;
    private final CreadorLinksCategoria creaLinks;
    private final RepositorioReceta repositorioReceta;

    public ControladorCategorias(RepositorioCategoria repositorio, CreadorLinksCategoria creaLinks, RepositorioReceta repositorioReceta) {
        this.repositorio = repositorio;
        this.creaLinks = creaLinks;
        this.repositorioReceta=repositorioReceta;
    }

    // Obtener todas las categorías (GET)
    @GetMapping
    public List<Categoria> obtenerCategorias() {
    	return repositorio.findAll();
    }

    // Obtener una categoría por su ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaPorId(@PathVariable("id") Long id) {
        Categoria categoria = repositorio.findById(id).orElseThrow(() -> new CategoriaNotFoundException(id));
        return ResponseEntity.ok(categoria);
    }
    
    @GetMapping("/receta/{idReceta}/categorias")
    public CollectionModel<EntityModel<Categoria>> obtenerCategoriasPorReceta(@PathVariable("idReceta") Long idReceta){
    	List<EntityModel<Categoria>> categorias = repositorio.findByRecetaId(idReceta).stream()
    			.map(creaLinks::toModel)
    			.collect(Collectors.toList());
    	
    	return CollectionModel.of(categorias,
    			linkTo(methodOn(ControladorCategorias.class).obtenerCategoriasPorReceta(idReceta))
                .withSelfRel()
    	);
    }

    // Crear una nueva categoría (POST)
    @PostMapping
    public ResponseEntity<EntityModel<Categoria>> crearCategoria(@RequestBody Categoria categoria) {
        EntityModel<Categoria> categoriaRes = creaLinks.toModel(repositorio.save(categoria));
        return ResponseEntity
                .created(categoriaRes.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(categoriaRes);
    }

    // Actualizar una categoría (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Categoria>> actualizarCategoria(@PathVariable("id") Long id, @RequestBody Categoria categoriaNueva) {

        Categoria categoriaActu = repositorio.findById(id)
                .map(categoria -> {
                    categoria.setNombre(categoriaNueva.getNombre());
                    // Agrega aquí más campos que quieras actualizar
                    return repositorio.save(categoria);
                })
                .orElseGet(() -> {
                    categoriaNueva.setId(id);
                    return repositorio.save(categoriaNueva);
                });

        EntityModel<Categoria> categoriaRes = creaLinks.toModel(categoriaActu);

        return ResponseEntity
                .created(categoriaRes.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(categoriaRes);
    }

    // Modificar parcialmente una categoría
    @PatchMapping("/{id}")
    public ResponseEntity<Categoria> actualizarParcialCategoria(@PathVariable("id") Long id, @RequestBody Categoria categoria) {
        Optional<Categoria> categoriaExistente = repositorio.findById(id);

        if (categoriaExistente.isPresent()) {
            Categoria categoriaActual = categoriaExistente.get();

            if (categoria.getNombre() != null) {
                categoriaActual.setNombre(categoria.getNombre());
            }

            Categoria categoriaActualizada = repositorio.save(categoriaActual);

            return ResponseEntity.ok(categoriaActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una categoría por su ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable("id") Long id) {
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{categoriaId}/recetas/{recetaId}")
    public ResponseEntity<?> agregarRecetaACategoria(@PathVariable("categoriaId") Long categoriaId, @PathVariable("recetaId") Long recetaId) {
    	 try {
    	        Categoria categoria = repositorio.findById(categoriaId).orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + categoriaId));
    	        Receta receta = repositorioReceta.findById(recetaId).orElseThrow(() -> new RuntimeException("Receta no encontrada con ID: " + recetaId));
    	        
    	        if (categoria.getRecetas().contains(receta)) {
    	            return ResponseEntity.badRequest().body("La receta ya está asociada a esta categoría");
    	        }
    	        
    	        categoria.getRecetas().add(receta);
    	        receta.getCategorias().add(categoria); // Asegúrate de que la receta también conoce la nueva categoría
    	        repositorio.save(categoria);
    	        repositorioReceta.save(receta); // Guarda también la receta

    	        return ResponseEntity.ok("Receta agregada a la categoría con éxito");
    	    } catch (Exception e) {
    	        return ResponseEntity.internalServerError().body("Error al agregar receta a categoría: " + e.getMessage());
    	    }
    }
}


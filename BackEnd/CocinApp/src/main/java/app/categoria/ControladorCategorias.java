package app.categoria;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.receta.ControladorRecetas;
import app.receta.Receta;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "http://localhost:4200/")
public class ControladorCategorias {

    private final RepositorioCategoria repositorio;
    private final CreadorLinksCategoria creaLinks;

    public ControladorCategorias(RepositorioCategoria repositorio, CreadorLinksCategoria creaLinks) {
        this.repositorio = repositorio;
        this.creaLinks = creaLinks;
    }

    // Obtener todas las categorías (GET)
    @GetMapping
    public List<Categoria> obtenerCategorias() {
    	return repositorio.findAll();
//        List<EntityModel<Categoria>> categorias = repositorio.findAll().stream()
//                .map(creaLinks::toModel)
//                .collect(Collectors.toList());
//
//        return CollectionModel.of(categorias,
//                linkTo(methodOn(ControladorCategorias.class).obtenerCategorias())
//                        .withSelfRel()
//        );
    }

    // Obtener una categoría por su ID (GET)
    @GetMapping("/{id}")
    public EntityModel<Categoria> obtenerCategoriaPorId(@PathVariable("id") Long id) {
        Categoria categoria = repositorio.findById(id).orElseThrow(() -> new CategoriaNotFoundException(id));
        return creaLinks.toModel(categoria);
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
}


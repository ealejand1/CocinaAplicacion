package com.example.cocina.API.ingrediente;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RepositorioIngrediente extends CrudRepository<Ingrediente, Long> {
	// Create
    Ingrediente save(Ingrediente ingrediente);

    // Selects
    Optional<Ingrediente> findById(Long id);
    List<Ingrediente> findAll();
    List<Ingrediente> findByNombre(@Param("nombre") String nombre); // Si deseas buscar por nombre de ingrediente

    // Update
    Ingrediente saveAndFlush(Ingrediente ingrediente);

    // Delete
    void deleteById(Long id);
    void delete(Ingrediente ingrediente);

    // Método para encontrar ingredientes por receta
//    @Query("SELECT i.ingredientes FROM RecetaIngrediente i WHERE i.receta.id = :recetaId")
//    List<Ingrediente> findByRecetaId(@Param("recetaId") Long recetaId);
}

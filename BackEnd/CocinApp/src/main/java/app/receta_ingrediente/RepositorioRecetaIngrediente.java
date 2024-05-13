package app.receta_ingrediente;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface RepositorioRecetaIngrediente extends CrudRepository<RecetaIngrediente, Long> {
    // Create
    RecetaIngrediente save(RecetaIngrediente recetaIngrediente);

    // Selects
    Optional<RecetaIngrediente> findById(Long id);
    List<RecetaIngrediente> findAll();
    List<RecetaIngrediente> findByUnidades(@Param("unidades") String unidades); // Si deseas buscar por unidades
    List<RecetaIngrediente> findByRecetaId(@Param("receta_id") Long receta_id);
    List<RecetaIngrediente> findByIngredienteId(@Param("ingrediente_id") Long ingrediente_id);
  
    // Update
    RecetaIngrediente saveAndFlush(RecetaIngrediente recetaIngrediente);

    // Delete
    void deleteById(Long id);
    void delete(RecetaIngrediente recetaIngrediente);
}



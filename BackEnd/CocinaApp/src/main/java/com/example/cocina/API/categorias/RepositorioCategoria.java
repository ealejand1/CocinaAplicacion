package com.example.cocina.API.categorias;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RepositorioCategoria extends CrudRepository<Categoria, Long> {
    // Create
    Categoria save(Categoria categoria);

    // Selects
    Optional<Categoria> findById(Long id);
    List<Categoria> findAll();
    List<Categoria> findByNombre(@Param("nombre") String nombre); // Si deseas buscar por nombre de categoría
    
    @Query("SELECT c FROM Categoria c JOIN c.recetas r WHERE r.id = :recetaId")
    List<Categoria> findByRecetaId(@Param("recetaId") Long recetaId);
    
    // Update
    Categoria saveAndFlush(Categoria categoria);

    // Delete
    void deleteById(Long id);
    void delete(Categoria categoria);
}

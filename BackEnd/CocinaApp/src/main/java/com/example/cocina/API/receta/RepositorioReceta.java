package com.example.cocina.API.receta;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RepositorioReceta extends CrudRepository<Receta, Long> {
    // Create
    Receta save(Receta receta);

    // Selects
    Optional<Receta> findById(Long id);
    List<Receta> findAll();
    List<Receta> findByNombre(@Param("nombre") String nombre); // Si deseas buscar por nombre de receta
    List<Receta> findByUsuarioId(Long userId);

    @Query("SELECT r FROM Receta r JOIN r.categorias c WHERE c.id = :categoriaId")
    List<Receta> findByCategoriaId(@Param("categoriaId") Long categoriaId);
    
    //Buscar por nombre parcial
    @Query("SELECT r FROM Receta r WHERE r.nombre LIKE %:nombre%")
    List<Receta> findByNombreParcial(@Param("nombre") String nombre);
  
    // Update
    Receta saveAndFlush(Receta receta);

    // Delete
    void deleteById(Long id);
    void delete(Receta receta);
}

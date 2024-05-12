package app.valoraciones;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.EntityModel;

public interface RepositorioValoracion extends PagingAndSortingRepository<Valoracion, Long>, CrudRepository<Valoracion, Long> {
    // Create
    Valoracion save(Valoracion valoracion);

    // Selects
    Optional<Valoracion> findById(Long id);
    List<Valoracion> findAll();
    List<Valoracion> findByPuntuacion(@Param("puntuacion") int puntuacion);
    List<Valoracion> findByRecetaId(Long recetaId);
    List<Valoracion> findByUsuarioId(Long userId);

    // Update
    Valoracion saveAndFlush(Valoracion valoracion);

    // Delete
    void deleteById(Long id);

    void delete(Valoracion valoracion);

}
package app.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface RepositorioUsuario extends PagingAndSortingRepository<Usuario, Long>, CrudRepository<Usuario, Long>{
	//Create
	Usuario save(Usuario usuario);
	
	//Selects
	Optional<Usuario> findById(Long id);
	List<Usuario> findAll();
	List<Usuario> findByNombre(@Param("nombre") String nombre);
	
	//Update
	Usuario saveAndFlush(Usuario usuario);
	
	//Delete
	void deleteById(Long id);
	void delete(Usuario usuario);

}

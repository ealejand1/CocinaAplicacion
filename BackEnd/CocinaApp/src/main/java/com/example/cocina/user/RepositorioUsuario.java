package com.example.cocina.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface RepositorioUsuario extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long>{
	//Create
	User save(User usuario);
	
	//Selects
	Optional<User> findById(Long id);
	List<User> findAll();
	
	@Query("SELECT u.rol FROM User u WHERE u.id = :id")
	Role findRolById(@Param("id") Long id);
	
	
	Optional<User> findByUsername(String username);
	//Update
	User saveAndFlush(User usuario);
	
	//Delete
	void deleteById(Long id);
	void delete(User usuario);

}

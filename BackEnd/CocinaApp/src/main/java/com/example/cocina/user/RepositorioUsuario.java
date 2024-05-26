package com.example.cocina.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RepositorioUsuario extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long>{
	//Create
	User save(User usuario);
	
	//Selects
	Optional<User> findById(Long id);
	List<User> findAll();
	
	Optional<User> findByUsername(String username);
	//Update
	User saveAndFlush(User usuario);
	
	//Delete
	void deleteById(Long id);
	void delete(User usuario);

}

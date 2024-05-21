package com.example.cocina.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositorio extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);
}

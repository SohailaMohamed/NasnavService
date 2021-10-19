package com.project.nasnav.rs.control;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	public boolean existsByEmail(String email);
	
	public User findByEmailAndPassword(String email, String password);
	
	public User findByEmail(String email);

}

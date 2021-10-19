package com.project.nasnav.rs.control;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageReository extends JpaRepository<ImageDetails, Integer> {
	
	public List<ImageDetails> findAllByStatus(String status);
	
	public List<ImageDetails> findAllByUserAndStatus(User user, String status);

}

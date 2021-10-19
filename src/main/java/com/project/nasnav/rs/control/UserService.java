package com.project.nasnav.rs.control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	List<ImageDetails> images = new ArrayList<>();

	public User getUser(String email, String password) {
		User temp;
		if (email.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin123")) {
			temp = new User(-1, "Admin", "admin123");
		}
		else {
			temp = userRepository.findByEmailAndPassword(email, password);
		}
		return temp;
	}
	
	public User getUserByEmail(String email) {
		User temp;
		if (email.equalsIgnoreCase("admin")) {
			temp = new User(-1, "Admin", "admin123");
		}
		else {
			temp = userRepository.findByEmail(email);
		}
		return temp;
	}

	public User addUser(User user) {
		// mail should be unique
		if (!userRepository.existsByEmail(user.getEmail())) {
			return userRepository.save(user);
		} else {
			return null;
		}
	}
	
}
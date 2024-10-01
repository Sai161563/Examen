package com.wipro.training.examen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.training.examen.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public Optional<User> findByEmail(String email);
	
	Optional<User> findByResetToken(String resetToken);

}
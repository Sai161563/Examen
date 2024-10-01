package com.wipro.training.examen.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wipro.training.examen.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
	
	public Optional<Admin> findByEmail(String email);

	Optional<Admin> findByResetToken(String resetToken);
}

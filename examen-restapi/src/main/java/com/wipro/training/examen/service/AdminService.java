package com.wipro.training.examen.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.training.examen.model.Admin;
import com.wipro.training.examen.repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepo;
	
	@Autowired
	  private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	  
	  public Admin registerAdmin(Admin admin) {
	    admin.setPassword(passwordEncoder.encode(admin.getPassword()));
	    return adminRepo.save(admin);
	  }
	  
	  public Optional<Admin> findByEmail(String email) {
		    return adminRepo.findByEmail(email);
		}
	  
	  public String generateResetToken(String email) {
	        Optional<Admin> adminOpt = adminRepo.findByEmail(email);
	        if (adminOpt.isPresent()) {
	            Admin admin = adminOpt.get();
	            SecureRandom random = new SecureRandom();
	            String token = String.format("%06d", random.nextInt(1000000));
	            admin.setResetToken(token);
	            admin.setTokenExpiryDate(LocalDate.now().plusDays(1));  // Token expires after 1 day
	            adminRepo.save(admin);
	            return token;
	        }
	        return null;
	    }

	    // Validate token and reset the password
	    public boolean resetPassword(String token, String newPassword) {
	        Optional<Admin> adminOpt = adminRepo.findByResetToken(token);
	        if (adminOpt.isPresent()) {
	            Admin admin = adminOpt.get();
	            if (admin.getTokenExpiryDate().isAfter(LocalDate.now())) {
	                admin.setPassword(passwordEncoder.encode(newPassword));  // Set the new password (hash it in real application)
	                admin.setResetToken(null);  // Invalidate the token after use
	                admin.setTokenExpiryDate(null);
	                adminRepo.save(admin);
	                return true;
	            }
	        }
	        return false;
	    }
	  
}

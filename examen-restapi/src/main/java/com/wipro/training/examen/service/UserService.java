package com.wipro.training.examen.service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wipro.training.examen.model.User;
import com.wipro.training.examen.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public User registerUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public Optional<User> findByEmail(String email) {
	    return userRepository.findByEmail(email);
	}
	
//	public String loginUser(String email, String password) {
//		Optional<User> user = userRepository.findByEmail(email);
//		if (user.isPresent()) {
//			if (passwordEncoder.matches(password, user.get().getPassword())) {
//				return "Logged in successfully";
//			} else {
//				return "Wrong password";
//			}
//		} else {
//			return "User not found";
//		}
//	}

	public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all users", e);
        }
    }

    public User getUserById(Long id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            return userOptional.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching user by ID", e);
        }
    }

    public User updateUserById(Long id, User user) {
        try {
            User existingUser = getUserById(id);
            if (existingUser != null) {
                existingUser.setFname(user.getFname());
                existingUser.setEmail(user.getEmail());
                existingUser.setMobileNo(user.getMobileNo());
                existingUser.setDob(user.getDob());
                existingUser.setCity(user.getCity());
                existingUser.setQualification(user.getQualification());
                existingUser.setState(user.getState());
                existingUser.setYear(user.getYear());
                return userRepository.save(existingUser);
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error updating user", e);
        }
    }
    
    public String generateResetToken(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            SecureRandom random = new SecureRandom();
            String token = String.format("%06d", random.nextInt(1000000));
            user.setResetToken(token);
            user.setTokenExpiryDate(LocalDate.now().plusDays(1));  // Token expires after 1 day
            userRepository.save(user);
            return token;
        }
        return null;
    }

    // Validate token and reset the password
    public boolean resetPassword(String token, String newPassword) {
        Optional<User> userOpt = userRepository.findByResetToken(token);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getTokenExpiryDate().isAfter(LocalDate.now())) {
                user.setPassword(passwordEncoder.encode(newPassword));  // Set the new password (hash it in real application)
                user.setResetToken(null);  // Invalidate the token after use
                user.setTokenExpiryDate(null);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }
}
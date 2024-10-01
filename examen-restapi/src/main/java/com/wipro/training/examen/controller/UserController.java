package com.wipro.training.examen.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.training.examen.dto.UserLoginDTO;
import com.wipro.training.examen.exception.ResourceNotFoundException;
import com.wipro.training.examen.model.User;
import com.wipro.training.examen.service.UserService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping(value="/api")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@PostMapping("/users/register")
	public ResponseEntity<Boolean> registerUser(@RequestBody User user) {
		try {
			userService.registerUser(user);
			return ResponseEntity.ok(true);
		} catch (Exception e) {
			return ResponseEntity.ok(false);
		}
	}

	@PostMapping("/users/login")
	public ResponseEntity<UserLoginDTO> loginUser(@Validated @RequestBody User user) 
			throws ResourceNotFoundException {
		String email = user.getEmail();
		String password = user.getPassword();

		User u = userService.findByEmail(email).orElseThrow(() -> 
		new ResourceNotFoundException("User doesn't exist ::" + email));

		UserLoginDTO userLoginDTO = null;
		if (email.equals(u.getEmail()) && passwordEncoder.matches(password, u.getPassword())) {
			userLoginDTO=new UserLoginDTO(u.getId(),u.getFname());

		}
		return ResponseEntity.ok(userLoginDTO);
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		try {
			List<User> users = userService.getAllUsers();
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		try {
			User user = userService.getUserById(id);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<Boolean> updateUserById(@PathVariable Long id, @RequestBody User user) {
		try {
			userService.updateUserById(id, user);
			return ResponseEntity.ok(true);
		} catch (Exception e) {
			return ResponseEntity.ok(false);
		}
	}

	@PostMapping("/forgot-password")
	public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		String resetToken = userService.generateResetToken(email);

		if (resetToken != null) {
			// Return token explicitly in response, not just in the message
			return ResponseEntity.ok(Map.of("message", "Password reset token generated.", "token", resetToken));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "User with email " + email + " not found."));
		}
	}

	@PostMapping("/reset-password")
	public ResponseEntity<ApiResponse> resetPassword(@RequestBody Map<String, String> request) {
		String token = request.get("token");
		String newPassword = request.get("newPassword");

		boolean result = userService.resetPassword(token, newPassword);
		if (result) {
			return ResponseEntity.ok(new ApiResponse("Password reset successfully"));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Invalid or expired reset token"));
		}
	}
	static class ApiResponse {
		private String message;

		public ApiResponse(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}

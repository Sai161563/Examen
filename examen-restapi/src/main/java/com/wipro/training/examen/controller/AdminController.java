package com.wipro.training.examen.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wipro.training.examen.exception.ResourceNotFoundException;
import com.wipro.training.examen.model.Admin;
import com.wipro.training.examen.service.AdminService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Admin admin) {
        try {
        	adminService.registerAdmin(admin);
            return new ResponseEntity<>("Registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid data: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/login")
	public ResponseEntity<Boolean> loginAdmin(@Validated @RequestBody Admin admin) 
	        throws ResourceNotFoundException {
	    Boolean isLoggedIn = false;
	    String email = admin.getEmail();
	    String password = admin.getPassword();
	    
	    Admin a = adminService.findByEmail(email).orElseThrow(() -> 
	            new ResourceNotFoundException("User doesn't exist ::" + email));
	    
	    if (email.equals(a.getEmail()) && passwordEncoder.matches(password, a.getPassword())) {
	        isLoggedIn = true;
	    }
	    return ResponseEntity.ok(isLoggedIn);
	    
	}
    
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String resetToken = adminService.generateResetToken(email);
        
        if (resetToken != null) {
            // Return token explicitly in response, not just in the message
            return ResponseEntity.ok(Map.of("message", "Password reset token generated.", "token", resetToken));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Admin with email " + email + " not found."));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        
        boolean result = adminService.resetPassword(token, newPassword);
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
//package com.wipro.training.examen.controller;
//
//import org.apache.commons.text.RandomStringGenerator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.wipro.training.examen.exception.ResourceNotFoundException;
//import com.wipro.training.examen.model.User;
//import com.wipro.training.examen.service.PasswordService;
//import com.wipro.training.examen.service.UserService;
//
//public class PasswordController {
//
//	@Autowired
//	private PasswordService service;
//	
//	@Autowired
//	private UserService userService;
//	
//	@Autowired
//	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//	
//	@PostMapping("/forgetpass")
//	public boolean triggerMail(@Validated @RequestBody User user) {
//		String email = user.getEmail();
//		User u = userService.findByEmail(email).orElseThrow(() -> 
//        	new ResourceNotFoundException("User doesn't exist ::" + email));
//		RandomStringGenerator rsg = new RandomStringGenerator.Builder()
//			    .selectFrom("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray())
//			    .build();
//		String newPass=rsg.generate(10);
//		String toMail = u.getEmail();
//		u.setPassword(passwordEncoder.encode(newPass));
//		userService.updateUserById(u.getId(),u);
//		service.sendSimpleEmail(toMail, "Your new password set to : "+newPass, "Exam Portal New Password");
//		return true;
//	}
//	
//	
//}

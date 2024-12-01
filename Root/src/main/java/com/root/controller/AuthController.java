package com.root.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.root.entity.Users;
import com.root.exception.UserAlreadyRegistered;
import com.root.exception.UserNotRegistered;
import com.root.model.UserLogin;
import com.root.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	@Autowired
	private UserService userService;

	@PostMapping("/Register")
	ResponseEntity<?> registerUser(@RequestBody Users user) throws UserAlreadyRegistered {
		userService.registerUser(user);
		return ResponseEntity.ok("Registration successful!");
	}

	@PostMapping("/login")
	ResponseEntity<?> LoginUser(@RequestBody UserLogin login) throws UserNotRegistered {
		userService.LoginUser(login);
		return ResponseEntity.ok("Login successful!");

	}

}

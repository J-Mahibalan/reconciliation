package com.project.reconciliation.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.reconciliation.entities.ERole;
import com.project.reconciliation.entities.Role;
import com.project.reconciliation.entities.UserEntity;
import com.project.reconciliation.security.jwt.JwtUtils;
import com.project.reconciliation.security.payload.request.LoginRequest;
import com.project.reconciliation.security.payload.request.SignupRequest;
import com.project.reconciliation.security.payload.response.JwtResponse;
import com.project.reconciliation.security.payload.response.MessageResponse;
import com.project.reconciliation.security.service.UserDetailsImpl;
import com.project.reconciliation.security.service.UserDetailsServiceImpl;
import com.project.reconciliation.service.RoleService;
import com.project.reconciliation.service.UserEntityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
 
	private static final Logger loggers = LoggerFactory.getLogger(AuthController.class);
	@Autowired
	AuthenticationManager authenticationManager;
 
	@Autowired
	JwtUtils jwtUtils;
 
	@Autowired
	UserDetailsServiceImpl userDetailsService;
 
	@Autowired
	UserEntityService userService;
 
	@Autowired
	RoleService roleService;
 
	@Autowired
	PasswordEncoder passwordEncoder;
 
	@PostMapping("/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
 
		loggers.info("User Sign In");
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
 
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
 
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String role = userDetails.getAuthorities().stream().findFirst() // Get the first authority
				.map(item -> item.getAuthority()) // Map it to its authority string
				.orElse(null);
 
		JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), role);
		loggers.info("User Sign In Successfull");
		return ResponseEntity.ok(jwtResponse);
	}
 
	@PostMapping("/signup")
	public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		loggers.info("User Sign Up");
		if (userService.existsByUsername(signUpRequest.getUsername())) {
			loggers.info("Username Already Exixts");
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}
 
		// Create new user's account
		UserEntity user = new UserEntity();
		user.setUsername(signUpRequest.getUsername());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setEmail(signUpRequest.getEmail());;
		Optional<Role> role = roleService.findRoleByName(ERole.ROLE_USER);
		if (role.isPresent()) {
			user.setRole(role.get());
		}
		userService.addUserEntity(user);
		loggers.info("User Sign Up Successfull");
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	
}
 
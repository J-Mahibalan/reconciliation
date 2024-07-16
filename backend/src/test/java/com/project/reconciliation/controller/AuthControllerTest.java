package com.project.reconciliation.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.reconciliation.controller.AuthController;
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

class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserEntityService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateUser_Success() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        // Mock UserDetails
        UserDetailsImpl userDetails = new UserDetailsImpl(1, "username", "password", "email", mock(GrantedAuthority.class));
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Mock Jwt Generation
        when(jwtUtils.generateJwtToken(authentication)).thenReturn("generated_token");

        // Test LoginRequest
        LoginRequest loginRequest = new LoginRequest("username", "password");

        // Test Authentication
        ResponseEntity<JwtResponse> response = authController.authenticateUser(loginRequest);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("generated_token", response.getBody().getAccessToken());
    }
    

    @Test
    void testRegisterUser_Success() {
        // Mock Sign-up Request
        SignupRequest signupRequest = new SignupRequest("username", "password", "email");

        // Mock UserEntity and Role
        UserEntity user = new UserEntity();
        user.setUsername("username");
        user.setPassword("password");
        user.setEmail("email");

        Role role = new Role();
        role.setName(ERole.ROLE_USER);

        when(userService.existsByUsername("username")).thenReturn(false);
        when(roleService.findRoleByName(ERole.ROLE_USER)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("password")).thenReturn("encoded_password");

        // Test Registration
        ResponseEntity<MessageResponse> response = authController.registerUser(signupRequest);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("User registered successfully!", response.getBody().getMessage());
    }

    @Test
    void testRegisterUser_UsernameTaken() {
        // Mock Sign-up Request
        SignupRequest signupRequest = new SignupRequest("username", "password", "email");

        when(userService.existsByUsername("username")).thenReturn(true);

        // Test Registration
        ResponseEntity<MessageResponse> response = authController.registerUser(signupRequest);

        // Assertions
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Error: Username is already taken!", response.getBody().getMessage());
    }
}

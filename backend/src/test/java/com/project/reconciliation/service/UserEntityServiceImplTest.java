package com.project.reconciliation.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.reconciliation.entities.ERole;
import com.project.reconciliation.entities.Role;
import com.project.reconciliation.entities.UserEntity;
import com.project.reconciliation.repositories.UserRepository;
import com.project.reconciliation.service.UserEntityServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserEntityServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserEntityServiceImpl userService;

    @Test
    void testUpdateRole_Success() {
        // Given
        Integer userId = 1;
        Role newRole = new Role(1,ERole.ROLE_ADMIN);
        UserEntity existingUser = new UserEntity("testUser", "password","testUser@gmail.com");
        existingUser.setUserId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(UserEntity.class))).thenReturn(existingUser);

        // When
        String result = userService.updateRole(userId, newRole);

        // Then
        assertEquals("Role Updated Successfully!!!", result);
        assertEquals(newRole, existingUser.getRole());
    }

    @Test
    void testUpdateRole_UserNotFound() {
        // Given
        Integer userId = 1;
        Role newRole = new Role(1, ERole.ROLE_ADMIN);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When
        String result = userService.updateRole(userId, newRole);

        // Then
        assertNull(result);
    }

    @Test
    void testAddUserEntity_Success() {
        // Given
        UserEntity newUser = new UserEntity("testUser", "password","testUser@gmail.com");

        when(userRepository.save(any(UserEntity.class))).thenReturn(newUser);

        // When
        UserEntity savedUser = userService.addUserEntity(newUser);

        // Then
        assertNotNull(savedUser);
        assertEquals("testUser", savedUser.getUsername());
    }

    @Test
    void testFindByUsername_UserExists() {
        // Given
        String username = "testUser";
        UserEntity existingUser = new UserEntity("username", "password","email");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));

        // When
        Optional<UserEntity> foundUser = userService.findByUsername(username);

        // Then
        assertTrue(foundUser.isPresent());
        assertNotEquals(username, foundUser.get().getUsername());
    }

    @Test
    void testFindByUsername_UserNotExists() {
        // Given
        String username = "nonExistingUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // When
        Optional<UserEntity> foundUser = userService.findByUsername(username);

        // Then
        assertTrue(foundUser.isEmpty());
    }

    @Test
    void testExistsByUsername_True() {
        // Given
        String existingUsername = "existingUser";

        when(userRepository.existsByUsername(existingUsername)).thenReturn(true);

        // When
        Boolean exists = userService.existsByUsername(existingUsername);

        // Then
        assertTrue(exists);
    }

    @Test
    void testExistsByUsername_False() {
        // Given
        String nonExistingUsername = "nonExistingUser";

        when(userRepository.existsByUsername(nonExistingUsername)).thenReturn(false);

        // When
        Boolean exists = userService.existsByUsername(nonExistingUsername);

        // Then
        assertFalse(exists);
    }
}

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
import com.project.reconciliation.repositories.RoleRepository;
import com.project.reconciliation.service.RoleServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void testFindRoleByName_Exists() {
        // Given
        ERole roleName = ERole.ROLE_USER;
        Role role = new Role(4, roleName);
        when(roleRepository.findByName(roleName)).thenReturn(Optional.of(role));

        // When
        Optional<Role> foundRole = roleService.findRoleByName(roleName);

        // Then
        assertTrue(foundRole.isPresent());
        assertEquals(roleName, foundRole.get().getName());
    }

    @Test
    void testFindRoleByName_NotExists() {
        // Given
        ERole roleName = ERole.ROLE_ADMIN;
        when(roleRepository.findByName(roleName)).thenReturn(Optional.empty());

        // When
        Optional<Role> foundRole = roleService.findRoleByName(roleName);

        // Then
        assertTrue(foundRole.isEmpty());
    }

    @Test
    void testFindRoleById_Exists() {
        // Given
        Integer roleId = 1;
        Role role = new Role(1, ERole.ROLE_ADMIN);
        role.setId(roleId);
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        // When
        Optional<Role> foundRole = roleService.findRoleById(roleId);

        // Then
        assertTrue(foundRole.isPresent());
        assertEquals(roleId, foundRole.get().getId());
    }

    @Test
    void testFindRoleById_NotExists() {
        // Given
        Integer roleId = 5; // Assuming role with ID 5 does not exist
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        // When
        Optional<Role> foundRole = roleService.findRoleById(roleId);

        // Then
        assertTrue(foundRole.isEmpty());
    }
}

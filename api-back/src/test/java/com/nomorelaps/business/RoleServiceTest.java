package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.out.persistence.interfaces.IRolePersistenceAdapter;
import com.nomorelaps.domain.models.Role;

/**
 * Unit tests for RoleService.
 * Verifies business logic for system roles management.
 */
@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private IRolePersistenceAdapter persistencePort;

    @InjectMocks
    private RoleService roleService;

    private Role testRole;

    @BeforeEach
    void setUp() {
        testRole = new Role(1L);
        testRole.setName("ADMIN");
        testRole.setDescription("System Administrator");
    }

    @Test
    @DisplayName("create - Should delegate to persistence port")
    void shouldCreateRole() {
        when(persistencePort.save(any(Role.class))).thenReturn(testRole);
        Role result = roleService.create(testRole);
        assertNotNull(result);
        assertEquals("ADMIN", result.getName());
        verify(persistencePort).save(testRole);
    }

    @Test
    @DisplayName("findById - Should return role when present")
    void shouldFindRoleById() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testRole));
        Optional<Role> result = roleService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().getName());
    }

    @Test
    @DisplayName("findById - Should return empty if not found")
    void shouldReturnEmptyIfNotFound() {
        when(persistencePort.findById(99L)).thenReturn(Optional.empty());
        Optional<Role> result = roleService.findById(99L);
        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("findAll - Should return all roles")
    void shouldReturnAllRoles() {
        when(persistencePort.findAll()).thenReturn(List.of(testRole));
        List<Role> result = roleService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("deleteById - Should delegate to persistence port")
    void shouldDeleteRole() {
        roleService.deleteById(1L);
        verify(persistencePort).deleteById(1L);
    }
}

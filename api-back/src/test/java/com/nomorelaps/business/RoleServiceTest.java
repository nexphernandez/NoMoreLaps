package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
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
 * Unit tests for RoleService covering all business methods.
 *
 * @author nexphernandez
 * @version 1.0.0
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
        testRole.setDescription("Administrator role");
    }

    // ── create ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create - Should delegate to persistence and return saved role")
    void shouldCreateRole() {
        when(persistencePort.save(any(Role.class))).thenReturn(testRole);

        Role result = roleService.create(testRole);

        assertNotNull(result);
        assertEquals("ADMIN", result.getName());
        verify(persistencePort).save(testRole);
    }

    // ── findById ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findById - Should return role when found")
    void shouldFindRoleById() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testRole));

        Optional<Role> result = roleService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().getName());
    }

    @Test
    @DisplayName("findById - Should return empty when role not found")
    void shouldReturnEmptyWhenRoleNotFound() {
        when(persistencePort.findById(99L)).thenReturn(Optional.empty());

        Optional<Role> result = roleService.findById(99L);

        assertFalse(result.isPresent());
    }

    // ── findAll ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findAll - Should return all roles")
    void shouldFindAllRoles() {
        Role user = new Role(2L);
        user.setName("USER");
        when(persistencePort.findAll()).thenReturn(Arrays.asList(testRole, user));

        List<Role> result = roleService.findAll();

        assertEquals(2, result.size());
        assertEquals("ADMIN", result.get(0).getName());
        assertEquals("USER", result.get(1).getName());
    }

    @Test
    @DisplayName("findAll - Should return empty list when no roles")
    void shouldReturnEmptyListWhenNoRoles() {
        when(persistencePort.findAll()).thenReturn(List.of());

        List<Role> result = roleService.findAll();

        assertTrue(result.isEmpty());
    }

    // ── deleteById ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("deleteById - Should call persistence deleteById")
    void shouldDeleteRoleById() {
        doNothing().when(persistencePort).deleteById(1L);

        roleService.deleteById(1L);

        verify(persistencePort).deleteById(1L);
    }
}

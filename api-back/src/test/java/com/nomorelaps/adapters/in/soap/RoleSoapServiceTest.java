package com.nomorelaps.adapters.in.soap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.nomorelaps.adapters.in.api.RoleResponse;
import com.nomorelaps.adapters.mapper.RoleMapper;
import com.nomorelaps.business.interfaces.IRoleService;
import com.nomorelaps.domain.models.Role;

/**
 * Unit tests for RoleSoapService.
 * Validates the retrieval of role information via SOAP adapter.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
class RoleSoapServiceTest {

    @MockitoBean
    private IRoleService roleService;

    @MockitoSpyBean
    private RoleMapper roleMapper;

    @Autowired
    private RoleSoapService roleSoapService;

    private Role sampleRole;

    @BeforeEach
    void setUp() {
        sampleRole = new Role(1L);
        sampleRole.setName("ADMIN");
        sampleRole.setDescription("System Administrator");
    }

    @Test
    @DisplayName("findAll - Success: Should return list of all roles")
    void shouldReturnAllRolesSuccessfully() {
        when(roleService.findAll()).thenReturn(List.of(sampleRole));

        List<RoleResponse> result = roleSoapService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("ADMIN", result.get(0).getName());
        verify(roleService).findAll();
    }

    @Test
    @DisplayName("findById - Success: Should return role details when found")
    void shouldReturnRoleByIdSuccessfully() {
        when(roleService.findById(1L)).thenReturn(Optional.of(sampleRole));

        RoleResponse result = roleSoapService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("ADMIN", result.getName());
        verify(roleService).findById(1L);
    }

    @Test
    @DisplayName("findById - Failure: Should return null when role does not exist")
    void shouldReturnNullWhenRoleNotFound() {
        when(roleService.findById(99L)).thenReturn(Optional.empty());

        RoleResponse result = roleSoapService.findById(99L);

        assertNull(result);
        verify(roleService).findById(99L);
        verify(roleMapper, never()).toResponse(any());
    }
}

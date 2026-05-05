package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.RoleResponse;
import com.nomorelaps.adapters.mapper.RoleMapper;
import com.nomorelaps.business.interfaces.IRoleService;
import com.nomorelaps.domain.models.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RoleSoapServiceTest {

    private IRoleService roleService;
    private RoleMapper roleMapper;
    private RoleSoapService roleSoapService;

    @BeforeEach
    void setUp() {
        roleService = mock(IRoleService.class);
        roleMapper = mock(RoleMapper.class);
        roleSoapService = new RoleSoapService(roleService, roleMapper);
    }

    @Test
    @DisplayName("findAll - Should return list of role responses")
    void shouldReturnListOfRoleResponses() {
        Role role = new Role(1L);
        RoleResponse response = new RoleResponse();
        response.setId(1L);

        when(roleService.findAll()).thenReturn(Collections.singletonList(role));
        when(roleMapper.toResponse(role)).thenReturn(response);

        List<RoleResponse> result = roleSoapService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());

        verify(roleService, times(1)).findAll();
        verify(roleMapper, times(1)).toResponse(role);
    }

    @Test
    @DisplayName("findById - Should return role response when found")
    void shouldReturnRoleResponseWhenFound() {
        Role role = new Role(1L);
        RoleResponse response = new RoleResponse();
        response.setId(1L);

        when(roleService.findById(1L)).thenReturn(Optional.of(role));
        when(roleMapper.toResponse(role)).thenReturn(response);

        RoleResponse result = roleSoapService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(roleService, times(1)).findById(1L);
        verify(roleMapper, times(1)).toResponse(role);
    }

    @Test
    @DisplayName("findById - Should return null when not found")
    void shouldReturnNullWhenNotFound() {
        when(roleService.findById(1L)).thenReturn(Optional.empty());

        RoleResponse result = roleSoapService.findById(1L);

        assertNull(result);

        verify(roleService, times(1)).findById(1L);
        verify(roleMapper, never()).toResponse(any());
    }
}

package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nomorelaps.adapters.in.api.RoleRequest;
import com.nomorelaps.business.interfaces.IRoleService;
import com.nomorelaps.domain.models.Role;

/**
 * Integration tests for RoleController.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@SpringBootTest
@AutoConfigureMockMvc
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IRoleService roleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/roles - Should create role")
    @WithMockUser(roles = "ADMIN")
    void shouldCreateRole() throws Exception {
        RoleRequest request = new RoleRequest();
        request.setName("SUPER_ADMIN");
        request.setDescription("All access");

        Role saved = new Role(1L);
        when(roleService.create(any(Role.class))).thenReturn(saved);

        mockMvc.perform(post("/api/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/roles/{id} - Should return role when found")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnRoleById() throws Exception {
        Role role = new Role(1L);
        role.setName("USER");
        when(roleService.findById(1L)).thenReturn(Optional.of(role));

        mockMvc.perform(get("/api/roles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/roles/{id} - Should return 404 when not found")
    @WithMockUser(roles = "ADMIN")
    void shouldReturn404WhenRoleNotFound() throws Exception {
        when(roleService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/roles/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/roles/{id} - Should return 204")
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteRole() throws Exception {
        doNothing().when(roleService).deleteById(1L);

        mockMvc.perform(delete("/api/roles/1"))
                .andExpect(status().isNoContent());
    }
}

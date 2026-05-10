package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nomorelaps.adapters.in.api.RoleRequest;
import com.nomorelaps.adapters.mapper.RoleMapper;
import com.nomorelaps.business.interfaces.IRoleService;
import com.nomorelaps.domain.models.Role;

/**
 * Integration tests for RoleController.
 * Verifies role creation, retrieval, and deletion with ADMIN privileges.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private IRoleService roleService;

    @MockitoSpyBean
    private RoleMapper roleMapper;

    private RoleRequest validRequest;
    private Role sampleRole;

    @BeforeEach
    void setUp() {
        validRequest = new RoleRequest();
        validRequest.setName("ADMIN");
        validRequest.setDescription("Administrator role");

        sampleRole = new Role(1L);
        sampleRole.setName("ADMIN");
        sampleRole.setDescription("Administrator role");
    }

    @Test
    @DisplayName("POST /api/roles - Success: Should create role")
    @WithMockUser(roles = "ADMIN")
    void shouldCreateRoleSuccessfully() throws Exception {
        when(roleService.create(any(Role.class))).thenReturn(sampleRole);

        mockMvc.perform(post("/api/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("ADMIN"));
    }

    @Test
    @DisplayName("GET /api/roles/{id} - Success: Should return role details")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnRoleByIdSuccessfully() throws Exception {
        when(roleService.findById(1L)).thenReturn(Optional.of(sampleRole));

        mockMvc.perform(get("/api/roles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("ADMIN"));
    }

    @Test
    @DisplayName("GET /api/roles/{id} - Failure: Should return 404 when missing")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnNotFoundWhenRoleIsMissing() throws Exception {
        when(roleService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/roles/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/roles/{id} - Success: Should return 204")
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteRoleSuccessfully() throws Exception {
        doNothing().when(roleService).deleteById(1L);

        mockMvc.perform(delete("/api/roles/1"))
                .andExpect(status().isNoContent());
    }
}

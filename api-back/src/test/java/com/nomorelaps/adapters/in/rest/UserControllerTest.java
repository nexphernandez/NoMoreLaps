package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
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
import com.nomorelaps.adapters.in.api.UserRequest;
import com.nomorelaps.business.interfaces.IUserService;
import com.nomorelaps.domain.models.User;

/**
 * Integration tests for UserController.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/users - Should return list of users (requires ADMIN)")
    @WithMockUser(roles = "ADMIN")
    void shouldReturnAllUsers() throws Exception {
        User u1 = new User(1L);
        u1.setName("Alice");
        User u2 = new User(2L);
        u2.setName("Bob");
        when(userService.findAll()).thenReturn(Arrays.asList(u1, u2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("GET /api/users/{id} - Found")
    @WithMockUser
    void shouldReturnUserById() throws Exception {
        User user = new User(1L);
        user.setName("Alice");
        user.setEmail("alice@test.com");
        when(userService.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("GET /api/users/{id} - Not Found")
    @WithMockUser
    void shouldReturn404WhenUserNotFound() throws Exception {
        when(userService.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/users - Created")
    @WithMockUser
    void shouldCreateUser() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("Charlie");
        request.setEmail("charlie@test.com");
        request.setPassword("securePass");

        User saved = new User(3L);
        saved.setName("Charlie");
        when(userService.create(any(User.class))).thenReturn(saved);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    @DisplayName("DELETE /api/users/{id} - No Content")
    @WithMockUser
    void shouldDeleteUser() throws Exception {
        doNothing().when(userService).deleteById(1L);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("GET /api/users/email/{email} - Found")
    @WithMockUser
    void shouldReturnUserByEmail() throws Exception {
        User user = new User(1L);
        user.setEmail("alice@test.com");
        when(userService.findByEmail("alice@test.com")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/email/alice@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("alice@test.com"));
    }

    @Test
    @DisplayName("GET /api/users/email/{email} - Not Found")
    @WithMockUser
    void shouldReturn404WhenUserEmailNotFound() throws Exception {
        when(userService.findByEmail("none@test.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/email/none@test.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/users/{id} - Updated")
    @WithMockUser
    void shouldUpdateUser() throws Exception {
        UserRequest request = new UserRequest();
        request.setName("Alice Updated");
        request.setEmail("alice@test.com");
        request.setPassword("newPass");

        User updated = new User(1L);
        updated.setName("Alice Updated");
        when(userService.update(any(User.class))).thenReturn(updated);

        mockMvc.perform(put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alice Updated"));
    }
}


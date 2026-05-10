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

import com.nomorelaps.adapters.in.api.UserResponse;
import com.nomorelaps.adapters.mapper.UserMapper;
import com.nomorelaps.business.interfaces.IUserService;
import com.nomorelaps.domain.models.User;

/**
 * Unit tests for UserSoapService.
 * Validates the retrieval of user information via SOAP adapter.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
class UserSoapServiceTest {

    @MockitoBean
    private IUserService userService;

    @MockitoSpyBean
    private UserMapper userMapper;

    @Autowired
    private UserSoapService userSoapService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User(1L);
        sampleUser.setName("John Doe");
        sampleUser.setEmail("john@example.com");
    }

    @Test
    @DisplayName("findAll - Success: Should return list of all users")
    void shouldReturnAllUsersSuccessfully() {
        when(userService.findAll()).thenReturn(List.of(sampleUser));

        List<UserResponse> result = userSoapService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(userService).findAll();
    }

    @Test
    @DisplayName("findById - Success: Should return user details when found")
    void shouldReturnUserByIdSuccessfully() {
        when(userService.findById(1L)).thenReturn(Optional.of(sampleUser));

        UserResponse result = userSoapService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("john@example.com", result.getEmail());
        verify(userService).findById(1L);
    }

    @Test
    @DisplayName("findById - Failure: Should return null when user does not exist")
    void shouldReturnNullWhenUserNotFound() {
        when(userService.findById(99L)).thenReturn(Optional.empty());

        UserResponse result = userSoapService.findById(99L);

        assertNull(result);
        verify(userService).findById(99L);
        verify(userMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("findByEmail - Success: Should return user details when email exists")
    void shouldReturnUserByEmailSuccessfully() {
        when(userService.findByEmail("john@example.com")).thenReturn(Optional.of(sampleUser));

        UserResponse result = userSoapService.findByEmail("john@example.com");

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        verify(userService).findByEmail("john@example.com");
    }

    @Test
    @DisplayName("findByEmail - Failure: Should return null when email does not exist")
    void shouldReturnNullWhenEmailNotFound() {
        when(userService.findByEmail("ghost@example.com")).thenReturn(Optional.empty());

        UserResponse result = userSoapService.findByEmail("ghost@example.com");

        assertNull(result);
        verify(userService).findByEmail("ghost@example.com");
        verify(userMapper, never()).toResponse(any());
    }
}

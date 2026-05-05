package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.UserResponse;
import com.nomorelaps.adapters.mapper.UserMapper;
import com.nomorelaps.business.interfaces.IUserService;
import com.nomorelaps.domain.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserSoapServiceTest {

    private IUserService userService;
    private UserMapper userMapper;
    private UserSoapService userSoapService;

    @BeforeEach
    void setUp() {
        userService = mock(IUserService.class);
        userMapper = mock(UserMapper.class);
        userSoapService = new UserSoapService(userService, userMapper);
    }

    @Test
    @DisplayName("findAll - Should return list of user responses")
    void shouldReturnListOfUserResponses() {
        User user = new User(1L);
        UserResponse response = new UserResponse();
        response.setId(1L);

        when(userService.findAll()).thenReturn(Collections.singletonList(user));
        when(userMapper.toResponse(user)).thenReturn(response);

        List<UserResponse> result = userSoapService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());

        verify(userService, times(1)).findAll();
        verify(userMapper, times(1)).toResponse(user);
    }

    @Test
    @DisplayName("findById - Should return user response when found")
    void shouldReturnUserResponseWhenFound() {
        User user = new User(1L);
        UserResponse response = new UserResponse();
        response.setId(1L);

        when(userService.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(response);

        UserResponse result = userSoapService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(userService, times(1)).findById(1L);
        verify(userMapper, times(1)).toResponse(user);
    }

    @Test
    @DisplayName("findById - Should return null when not found")
    void shouldReturnNullWhenNotFound() {
        when(userService.findById(1L)).thenReturn(Optional.empty());

        UserResponse result = userSoapService.findById(1L);

        assertNull(result);

        verify(userService, times(1)).findById(1L);
        verify(userMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("findByEmail - Should return user response when found")
    void shouldReturnUserResponseWhenEmailFound() {
        User user = new User(1L);
        UserResponse response = new UserResponse();
        response.setId(1L);

        when(userService.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(userMapper.toResponse(user)).thenReturn(response);

        UserResponse result = userSoapService.findByEmail("test@test.com");

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(userService, times(1)).findByEmail("test@test.com");
        verify(userMapper, times(1)).toResponse(user);
    }

    @Test
    @DisplayName("findByEmail - Should return null when not found")
    void shouldReturnNullWhenEmailNotFound() {
        when(userService.findByEmail("test@test.com")).thenReturn(Optional.empty());

        UserResponse result = userSoapService.findByEmail("test@test.com");

        assertNull(result);

        verify(userService, times(1)).findByEmail("test@test.com");
        verify(userMapper, never()).toResponse(any());
    }
}

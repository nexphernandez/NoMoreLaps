package com.nomorelaps.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nomorelaps.adapters.in.api.AuthRequest;
import com.nomorelaps.adapters.in.api.AuthResponse;
import com.nomorelaps.adapters.in.api.UserRequest;
import com.nomorelaps.adapters.in.api.UserResponse;
import com.nomorelaps.adapters.mapper.UserMapper;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.UserJpaRepository;
import com.nomorelaps.business.interfaces.IUserService;
import com.nomorelaps.domain.models.User;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private IUserService userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserJpaRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("register - Should save user and return response")
    void shouldRegisterUser() {
        UserRequest request = new UserRequest();
        User domain = new User(1L);
        UserResponse response = new UserResponse(1L);

        when(userMapper.toDomainFromRequest(request)).thenReturn(domain);
        when(userService.create(domain)).thenReturn(domain);
        when(userMapper.toResponse(domain)).thenReturn(response);

        UserResponse result = authService.register(request);

        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("login - Should return token when successful")
    void shouldLoginSuccessfully() {
        AuthRequest request = new AuthRequest();
        request.setEmail("test@test.com");
        request.setPassword("pass");

        UserJpaEntity entity = new UserJpaEntity();
        entity.setEmail("test@test.com");
        entity.setPassword("encoded");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(entity));
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("token");

        AuthResponse response = authService.login(request);

        assertEquals("token", response.getToken());
        verify(authenticationManager).authenticate(any());
    }

    @Test
    @DisplayName("login - Should throw exception when user not found")
    void shouldThrowExceptionOnUserNotFound() {
        AuthRequest request = new AuthRequest();
        request.setEmail("missing@test.com");

        when(userRepository.findByEmail("missing@test.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.login(request));
    }
}

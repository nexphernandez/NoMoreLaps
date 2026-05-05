package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.AuthRequest;
import com.nomorelaps.adapters.in.api.AuthResponse;
import com.nomorelaps.adapters.in.api.UserRequest;
import com.nomorelaps.adapters.in.api.UserResponse;
import com.nomorelaps.adapters.mapper.UserMapper;
import com.nomorelaps.business.interfaces.IUserService;
import com.nomorelaps.domain.models.User;
import com.nomorelaps.infrastructure.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthSoapServiceTest {

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtService jwtService;
    private IUserService userService;
    private UserMapper userMapper;
    private AuthSoapService authSoapService;

    @BeforeEach
    void setUp() {
        authenticationManager = mock(AuthenticationManager.class);
        userDetailsService = mock(UserDetailsService.class);
        jwtService = mock(JwtService.class);
        userService = mock(IUserService.class);
        userMapper = mock(UserMapper.class);
        authSoapService = new AuthSoapService(authenticationManager, userDetailsService, jwtService, userService, userMapper);
    }

    @Test
    @DisplayName("login - Should authenticate and return token")
    void shouldLoginAndReturnToken() {
        AuthRequest request = new AuthRequest();
        request.setEmail("test@test.com");
        request.setPassword("password");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("test@test.com")).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("fake-jwt-token");

        AuthResponse response = authSoapService.login(request);

        assertNotNull(response);
        assertEquals("fake-jwt-token", response.getToken());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService, times(1)).loadUserByUsername("test@test.com");
        verify(jwtService, times(1)).generateToken(userDetails);
    }

    @Test
    @DisplayName("register - Should save user and return response")
    void shouldRegisterAndReturnResponse() {
        UserRequest request = new UserRequest();
        User domain = new User(1L);
        User savedDomain = new User(1L);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(1L);

        when(userMapper.toDomainFromRequest(request)).thenReturn(domain);
        when(userService.create(domain)).thenReturn(savedDomain);
        when(userMapper.toResponse(savedDomain)).thenReturn(userResponse);

        UserResponse response = authSoapService.register(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());

        verify(userMapper, times(1)).toDomainFromRequest(request);
        verify(userService, times(1)).create(domain);
        verify(userMapper, times(1)).toResponse(savedDomain);
    }
}

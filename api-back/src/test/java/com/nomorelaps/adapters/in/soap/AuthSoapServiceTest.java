package com.nomorelaps.adapters.in.soap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.nomorelaps.adapters.in.api.AuthRequest;
import com.nomorelaps.adapters.in.api.AuthResponse;
import com.nomorelaps.adapters.in.api.UserRequest;
import com.nomorelaps.adapters.in.api.UserResponse;
import com.nomorelaps.adapters.mapper.UserMapper;
import com.nomorelaps.business.interfaces.IUserService;
import com.nomorelaps.domain.models.User;
import com.nomorelaps.infrastructure.security.JwtService;

/**
 * Unit tests for AuthSoapService.
 * Validates login and registration processes via SOAP adapter.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
class AuthSoapServiceTest {

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private IUserService userService;

    @MockitoSpyBean
    private UserMapper userMapper;

    @Autowired
    private AuthSoapService authSoapService;

    private AuthRequest validAuthRequest;
    private UserRequest validUserRequest;
    private User sampleUser;

    @BeforeEach
    void setUp() {
        validAuthRequest = new AuthRequest();
        validAuthRequest.setEmail("test@test.com");
        validAuthRequest.setPassword("password123");

        validUserRequest = new UserRequest();
        validUserRequest.setEmail("test@test.com");
        validUserRequest.setName("Test User");
        validUserRequest.setPassword("password123");

        sampleUser = new User(1L);
        sampleUser.setEmail("test@test.com");
        sampleUser.setName("Test User");
    }

    @Test
    @DisplayName("login - Success: Should return JWT token when credentials are valid")
    void shouldLoginSuccessfully() {
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(validAuthRequest.getEmail())).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("fake-jwt-token");

        AuthResponse response = authSoapService.login(validAuthRequest);

        assertNotNull(response);
        assertEquals("fake-jwt-token", response.getToken());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("register - Success: Should return user data when registration is successful")
    void shouldRegisterSuccessfully() {
        when(userService.create(any(User.class))).thenReturn(sampleUser);

        UserResponse response = authSoapService.register(validUserRequest);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("test@test.com", response.getEmail());
        verify(userService).create(any(User.class));
    }
}

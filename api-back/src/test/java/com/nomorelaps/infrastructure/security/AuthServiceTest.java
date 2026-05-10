package com.nomorelaps.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nomorelaps.adapters.in.api.AuthRequest;
import com.nomorelaps.adapters.in.api.AuthResponse;
import com.nomorelaps.adapters.in.api.CompanyRequest;
import com.nomorelaps.adapters.in.api.UserRequest;
import com.nomorelaps.adapters.in.api.UserResponse;
import com.nomorelaps.adapters.mapper.CompanyMapper;
import com.nomorelaps.adapters.mapper.RoleMapper;
import com.nomorelaps.adapters.mapper.UserMapper;
import com.nomorelaps.business.interfaces.ICompanyService;
import com.nomorelaps.business.interfaces.IUserService;
import com.nomorelaps.domain.models.Company;
import com.nomorelaps.domain.models.Role;
import com.nomorelaps.domain.models.User;
import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.RoleJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.CompanyJpaRepository;
import com.nomorelaps.adapters.out.persistence.repository.RoleJpaRepository;
import com.nomorelaps.adapters.out.persistence.repository.UserJpaRepository;

/**
 * Unit tests for AuthService.
 * Adheres to the New Backend Test Refactoring Plan for granularity and business naming.
 * Verifies high-level authentication flows: user/company registration and login.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private AuthenticationManager authenticationManager;
    @Mock private IUserService userService;
    @Mock private ICompanyService companyService;
    @Mock private UserMapper userMapper;
    @Mock private CompanyMapper companyMapper;
    @Mock private RoleMapper roleMapper;
    @Mock private JwtService jwtService;
    @Mock private UserJpaRepository userJpaRepository;
    @Mock private RoleJpaRepository roleJpaRepository;
    @Mock private CompanyJpaRepository companyJpaRepository;

    @InjectMocks
    private AuthService authService;

    private User testUser;
    private Role testRole;
    private RoleJpaEntity testRoleEntity;
    private UserJpaEntity testUserEntity;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setEmail("test@test.com");
        
        testRole = new Role();
        testRole.setName("USER");
        
        testRoleEntity = new RoleJpaEntity();
        testRoleEntity.setName("USER");

        testUserEntity = new UserJpaEntity();
        testUserEntity.setId(1L);
        testUserEntity.setEmail("test@test.com");
    }


    @Test
    @DisplayName("register - No Password: Should throw IllegalArgumentException")
    void register_NoPassword_ShouldThrowException() {
        UserRequest registrationRequest = new UserRequest();
        registrationRequest.setPassword(null);
        assertThrows(IllegalArgumentException.class, () -> authService.register(registrationRequest));
    }

    @Test
    @DisplayName("register - Blank Password: Should throw IllegalArgumentException")
    void register_PasswordBlank_ShouldThrowException() {
        UserRequest registrationRequest = new UserRequest();
        registrationRequest.setPassword("   ");
        assertThrows(IllegalArgumentException.class, () -> authService.register(registrationRequest));
    }

    @Test
    @DisplayName("register - Role Found: Should assign USER role")
    void register_RoleFound_ShouldAssignUserRole() {
        UserRequest registrationRequest = new UserRequest();
        registrationRequest.setPassword("pass");

        when(userMapper.toDomainFromRequest(registrationRequest)).thenReturn(testUser);
        when(roleJpaRepository.findByName("USER")).thenReturn(Optional.of(testRoleEntity));
        when(roleMapper.toDomain(testRoleEntity)).thenReturn(testRole);
        when(userService.create(testUser)).thenReturn(testUser);
        when(userMapper.toResponse(testUser)).thenReturn(new UserResponse());

        authService.register(registrationRequest);

        assertEquals(testRole, testUser.getRole());
    }

    @Test
    @DisplayName("register - Role Missing: Should proceed without assigning role")
    void register_RoleMissing_ShouldNotThrowException() {
        UserRequest registrationRequest = new UserRequest();
        registrationRequest.setPassword("pass");

        when(userMapper.toDomainFromRequest(registrationRequest)).thenReturn(testUser);
        when(roleJpaRepository.findByName("USER")).thenReturn(Optional.empty());
        when(userService.create(testUser)).thenReturn(testUser);
        when(userMapper.toResponse(testUser)).thenReturn(new UserResponse());

        authService.register(registrationRequest);

        assertNull(testUser.getRole());
    }


    @Test
    @DisplayName("registerCompany - No Password: Should throw IllegalArgumentException")
    void registerCompany_NoPassword_ShouldThrowException() {
        CompanyRequest companyRegistrationRequest = new CompanyRequest();
        companyRegistrationRequest.setPassword("");
        assertThrows(IllegalArgumentException.class, () -> authService.registerCompany(companyRegistrationRequest));
    }

    @Test
    @DisplayName("registerCompany - Null Password: Should throw IllegalArgumentException")
    void registerCompany_PasswordNull_ShouldThrowException() {
        CompanyRequest companyRegistrationRequest = new CompanyRequest();
        companyRegistrationRequest.setPassword(null);
        assertThrows(IllegalArgumentException.class, () -> authService.registerCompany(companyRegistrationRequest));
    }

    @Test
    @DisplayName("registerCompany - Success: Should link company to saved user")
    void registerCompany_Success_ShouldLinkUser() {
        CompanyRequest companyRegistrationRequest = new CompanyRequest();
        companyRegistrationRequest.setPassword("pass");
        companyRegistrationRequest.setEmail("corp@test.com");
        
        User savedUser = new User();
        savedUser.setEmail("corp@test.com");
        Company testCompany = new Company();

        when(userService.create(any())).thenReturn(savedUser);
        when(companyMapper.toDomainFromRequest(companyRegistrationRequest)).thenReturn(testCompany);
        when(userJpaRepository.findByEmail(anyString())).thenReturn(Optional.of(testUserEntity));

        authService.registerCompany(companyRegistrationRequest);

        assertEquals(savedUser, testCompany.getUser());
        assertEquals("corp@test.com", testCompany.getEmail());
    }

    @Test
    @DisplayName("registerCompany - Role Found: Should assign COMPANY role to user domain")
    void registerCompany_RoleFound_ShouldAssignRole() {
        CompanyRequest companyRegistrationRequest = new CompanyRequest();
        companyRegistrationRequest.setPassword("pass");
        companyRegistrationRequest.setEmail("corp@test.com");
        
        Role companyRole = new Role();
        companyRole.setName("COMPANY");

        when(roleJpaRepository.findByName("COMPANY")).thenReturn(Optional.of(testRoleEntity));
        when(roleMapper.toDomain(testRoleEntity)).thenReturn(companyRole);
        when(userService.create(any(User.class))).thenReturn(new User());
        when(companyMapper.toDomainFromRequest(companyRegistrationRequest)).thenReturn(new Company());
        when(userJpaRepository.findByEmail(any())).thenReturn(Optional.of(testUserEntity));

        authService.registerCompany(companyRegistrationRequest);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).create(userCaptor.capture());
        assertEquals(companyRole, userCaptor.getValue().getRole());
    }

    @Test
    @DisplayName("registerCompany - Role Missing: Should not assign role to user domain")
    void registerCompany_RoleMissing_ShouldNotAssignRole() {
        CompanyRequest companyRegistrationRequest = new CompanyRequest();
        companyRegistrationRequest.setPassword("pass");
        
        User savedUser = new User();
        Company testCompany = new Company();

        when(userService.create(any())).thenReturn(savedUser);
        when(companyMapper.toDomainFromRequest(companyRegistrationRequest)).thenReturn(testCompany);
        when(roleJpaRepository.findByName("COMPANY")).thenReturn(Optional.empty());
        when(userJpaRepository.findByEmail(any())).thenReturn(Optional.of(new UserJpaEntity()));

        authService.registerCompany(companyRegistrationRequest);

        assertNull(savedUser.getRole());
    }


    @Test
    @DisplayName("login - User Missing: Should throw UsernameNotFoundException")
    void login_UserNotFound_ShouldThrowException() {
        AuthRequest loginRequest = new AuthRequest("none@test.com", "pass");
        when(userJpaRepository.findByEmail("none@test.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.login(loginRequest));
    }

    @Test
    @DisplayName("login - No Company: Should return response without companyId")
    void login_NoCompany_ShouldReturnNullCompanyId() {
        AuthRequest loginRequest = new AuthRequest("test@test.com", "pass");

        when(userJpaRepository.findByEmail(anyString())).thenReturn(Optional.of(testUserEntity));
        when(companyJpaRepository.findByUserId(1L)).thenReturn(Optional.empty());

        AuthResponse loginResponse = authService.login(loginRequest);

        assertNull(loginResponse.getCompanyId());
    }

    @Test
    @DisplayName("login - Has Company: Should include companyId in response and claims")
    void login_HasCompany_ShouldIncludeCompanyId() {
        AuthRequest loginRequest = new AuthRequest("test@test.com", "pass");
        CompanyJpaEntity testCompanyEntity = new CompanyJpaEntity();
        testCompanyEntity.setId(500L);

        when(userJpaRepository.findByEmail(anyString())).thenReturn(Optional.of(testUserEntity));
        when(companyJpaRepository.findByUserId(1L)).thenReturn(Optional.of(testCompanyEntity));

        AuthResponse loginResponse = authService.login(loginRequest);

        assertEquals(500L, loginResponse.getCompanyId());
        verify(jwtService).generateToken(argThat(claimsMap -> claimsMap.get("companyId").equals(500L)), any());
    }
}

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

import com.nomorelaps.business.interfaces.ICompanyService;
import com.nomorelaps.adapters.mapper.RoleMapper;
import com.nomorelaps.adapters.mapper.CompanyMapper;
import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.RoleJpaEntity;
import com.nomorelaps.adapters.in.api.CompanyRequest;
import com.nomorelaps.domain.models.Company;
import com.nomorelaps.adapters.out.persistence.repository.RoleJpaRepository;
import com.nomorelaps.adapters.out.persistence.repository.CompanyJpaRepository;
import com.nomorelaps.infrastructure.security.JwtService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private IUserService userService;
    @Mock
    private ICompanyService companyService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private CompanyMapper companyMapper;
    @Mock
    private RoleMapper roleMapper;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserJpaRepository userRepository;
    @Mock
    private RoleJpaRepository roleRepository;
    @Mock
    private CompanyJpaRepository companyRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    @DisplayName("registerCompany - Should create user and company then return token")
    void shouldRegisterCompany() {
        CompanyRequest request = new CompanyRequest();
        request.setEmail("comp@test.com");
        request.setPassword("pass");
        request.setName("Comp Name");
        request.setCif("CIF123");

        User user = new User(1L);
        user.setEmail("comp@test.com");
        Company company = new Company(1L);

        UserJpaEntity userEntity = new UserJpaEntity();
        userEntity.setEmail("comp@test.com");

        when(roleRepository.findByName("COMPANY")).thenReturn(Optional.of(new RoleJpaEntity()));
        when(userService.create(any(User.class))).thenReturn(user);
        when(companyMapper.toDomainFromRequest(request)).thenReturn(company);
        when(userRepository.findByEmail("comp@test.com")).thenReturn(Optional.of(userEntity));
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("token");

        AuthResponse result = authService.registerCompany(request);

        assertNotNull(result);
        assertEquals("token", result.getToken());
        verify(companyService).create(company);
    }

    @Test
    @DisplayName("register - Should save user and return response")
    void shouldRegisterUser() {
        UserRequest request = new UserRequest();
        request.setPassword("password123");
        User domain = new User(1L);
        UserResponse response = new UserResponse(1L);

        when(roleRepository.findByName("USER")).thenReturn(Optional.of(new RoleJpaEntity()));
        when(userMapper.toDomainFromRequest(request)).thenReturn(domain);
        when(userService.create(domain)).thenReturn(domain);
        when(userMapper.toResponse(domain)).thenReturn(response);

        UserResponse result = authService.register(request);

        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("login - Should return token and companyId when user has companies")
    void shouldLoginWithCompanyId() {
        AuthRequest request = new AuthRequest();
        request.setEmail("admin@company.com");
        request.setPassword("pass");

        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(10L);
        entity.setEmail("admin@company.com");
        
        CompanyJpaEntity companyEntity = new CompanyJpaEntity();
        companyEntity.setId(500L);

        when(userRepository.findByEmail("admin@company.com")).thenReturn(Optional.of(entity));
        when(companyRepository.findByUserId(10L)).thenReturn(Optional.of(companyEntity));
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("token");

        AuthResponse response = authService.login(request);

        assertEquals("token", response.getToken());
        assertEquals(500L, response.getCompanyId());
    }

    @Test
    @DisplayName("login - Should return null companyId when user has empty companies set")
    void shouldLoginWithEmptyCompanies() {
        AuthRequest request = new AuthRequest();
        request.setEmail("user@test.com");
        request.setPassword("pass");

        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(10L);
        entity.setEmail("user@test.com");

        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(entity));
        when(companyRepository.findByUserId(10L)).thenReturn(Optional.empty());
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("token");

        AuthResponse response = authService.login(request);

        assertNull(response.getCompanyId());
    }

    @Test
    @DisplayName("login - Should return null companyId when user companies is null")
    void shouldLoginWithNullCompanies() {
        AuthRequest request = new AuthRequest();
        request.setEmail("user@test.com");
        request.setPassword("pass");

        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(10L);
        entity.setEmail("user@test.com");

        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(entity));
        when(companyRepository.findByUserId(10L)).thenReturn(Optional.empty());
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("token");

        AuthResponse response = authService.login(request);

        assertNull(response.getCompanyId());
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

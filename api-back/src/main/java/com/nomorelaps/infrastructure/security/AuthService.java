package com.nomorelaps.infrastructure.security;

import com.nomorelaps.adapters.in.api.AuthRequest;
import com.nomorelaps.adapters.in.api.AuthResponse;
import com.nomorelaps.adapters.in.api.UserRequest;
import com.nomorelaps.adapters.in.api.UserResponse;
import com.nomorelaps.adapters.mapper.UserMapper;
import com.nomorelaps.business.interfaces.IUserService;
import com.nomorelaps.domain.models.User;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.UserJpaRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service to handle the business logic of authentication and registration.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
import com.nomorelaps.adapters.in.api.CompanyRequest;
import com.nomorelaps.adapters.mapper.CompanyMapper;
import com.nomorelaps.adapters.mapper.RoleMapper;
import com.nomorelaps.business.interfaces.ICompanyService;
import com.nomorelaps.domain.models.Company;

import com.nomorelaps.adapters.out.persistence.jpa.RoleJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.RoleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final ICompanyService companyService;
    private final UserMapper userMapper;
    private final CompanyMapper companyMapper;
    private final RoleMapper roleMapper;
    private final JwtService jwtService;
    private final UserJpaRepository userRepository;
    private final RoleJpaRepository roleRepository;

    public AuthService(AuthenticationManager authenticationManager, 
                       IUserService userService, 
                       ICompanyService companyService,
                       UserMapper userMapper, 
                       CompanyMapper companyMapper,
                       RoleMapper roleMapper,
                       JwtService jwtService,
                       UserJpaRepository userRepository,
                       RoleJpaRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.companyService = companyService;
        this.userMapper = userMapper;
        this.companyMapper = companyMapper;
        this.roleMapper = roleMapper;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Registers a new user and returns their information.
     * @param request The user registration data.
     * @return UserResponse.
     */
    public UserResponse register(UserRequest request) {
        User domain = userMapper.toDomainFromRequest(request);
        
        // Assign default ROLE_USER to maintain compatibility with mobile app/user api
        roleRepository.findByName("ROLE_USER")
            .ifPresent(roleEntity -> domain.setRole(roleMapper.toDomain(roleEntity)));
            
        User saved = userService.create(domain);
        return userMapper.toResponse(saved);
    }

    /**
     * Registers a new company and its admin user.
     * @param request The company registration data.
     * @return AuthResponse with JWT.
     */
    public AuthResponse registerCompany(CompanyRequest request) {
        // 1. Create the user associated with the company
        User userDomain = new User();
        userDomain.setName(request.getName());
        userDomain.setEmail(request.getEmail());
        userDomain.setPassword(request.getPassword());
        
        // Assign ROLE_COMPANY specifically for portal registrations
        roleRepository.findByName("ROLE_COMPANY")
            .ifPresent(roleEntity -> userDomain.setRole(roleMapper.toDomain(roleEntity)));
            
        User savedUser = userService.create(userDomain);

        // 2. Create the company linked to the user
        Company companyDomain = companyMapper.toDomainFromRequest(request);
        companyDomain.setUser(savedUser);
        companyService.create(companyDomain);

        // 3. Perform login
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail(request.getEmail());
        authRequest.setPassword(request.getPassword());
        return login(authRequest);
    }

    /**
     * Authenticates credentials and returns a JWT token.
     * @param request Authentication credentials.
     * @return AuthResponse with JWT.
     */
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserJpaEntity userEntity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        UserDetails userDetails = new SecurityUser(userEntity);
        String jwtToken = jwtService.generateToken(userDetails);

        Long companyId = null;
        if (userEntity.getCompanies() != null && !userEntity.getCompanies().isEmpty()) {
            companyId = userEntity.getCompanies().iterator().next().getId();
        }

        return new AuthResponse(jwtToken, "Login successful", companyId, userEntity.getEmail(), userEntity.getName());
    }
}

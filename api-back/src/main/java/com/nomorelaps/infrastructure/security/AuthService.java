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
import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.CompanyJpaRepository;

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
    private final CompanyJpaRepository companyRepository;

    public AuthService(AuthenticationManager authenticationManager,
            IUserService userService,
            ICompanyService companyService,
            UserMapper userMapper,
            CompanyMapper companyMapper,
            RoleMapper roleMapper,
            JwtService jwtService,
            UserJpaRepository userRepository,
            RoleJpaRepository roleRepository,
            CompanyJpaRepository companyRepository) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.companyService = companyService;
        this.userMapper = userMapper;
        this.companyMapper = companyMapper;
        this.roleMapper = roleMapper;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
    }

    /**
     * Registers a new user and returns their information.
     * 
     * @param request The user registration data.
     * @return UserResponse.
     */
    public UserResponse register(UserRequest request) {
        User domain = userMapper.toDomainFromRequest(request);

        roleRepository.findByName("USER")
                .ifPresent(roleEntity -> domain.setRole(roleMapper.toDomain(roleEntity)));

        User saved = userService.create(domain);
        return userMapper.toResponse(saved);
    }

    /**
     * Registers a new company and its admin user.
     * 
     * @param request The company registration data.
     * @return AuthResponse with JWT.
     */
    public AuthResponse registerCompany(CompanyRequest request) {
        // 1. Create the user associated with the company
        User userDomain = new User();
        userDomain.setName(request.getName());
        userDomain.setEmail(request.getEmail());
        userDomain.setPassword(request.getPassword());

        roleRepository.findByName("COMPANY")
                .ifPresent(roleEntity -> userDomain.setRole(roleMapper.toDomain(roleEntity)));

        User savedUser = userService.create(userDomain);

        // 2. Create the company linked to the user
        Company companyDomain = companyMapper.toDomainFromRequest(request);
        companyDomain.setUser(savedUser);
        companyDomain.setEmail(savedUser.getEmail()); // Ensure email consistency
        companyService.create(companyDomain);

        // 3. Perform login
        AuthRequest authRequest = new AuthRequest();
        authRequest.setEmail(request.getEmail());
        authRequest.setPassword(request.getPassword());
        return login(authRequest);
    }

    /**
     * Authenticates credentials and returns a JWT token.
     * 
     * @param request Authentication credentials.
     * @return AuthResponse with JWT.
     */
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        UserJpaEntity userEntity = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserDetails userDetails = new SecurityUser(userEntity);
        String jwtToken = jwtService.generateToken(userDetails);

        // Fallback search by userId if companies collection is empty
        Long companyId = companyRepository.findByUserId(userEntity.getId())
                .map(CompanyJpaEntity::getId)
                .orElse(null);

        return new AuthResponse(jwtToken, "Login successful", companyId, userEntity.getEmail(), userEntity.getName());
    }
}

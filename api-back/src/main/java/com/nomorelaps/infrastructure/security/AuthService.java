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
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final UserJpaRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, 
                       IUserService userService, 
                       UserMapper userMapper, 
                       JwtService jwtService,
                       UserJpaRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user and returns their information.
     * @param request The user registration data.
     * @return UserResponse.
     */
    public UserResponse register(UserRequest request) {
        User domain = userMapper.toDomainFromRequest(request);
        User saved = userService.create(domain);
        return userMapper.toResponse(saved);
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

        return new AuthResponse(jwtToken, "Login successful");
    }
}

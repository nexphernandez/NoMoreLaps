package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.AuthRequest;
import com.nomorelaps.adapters.in.api.AuthResponse;
import com.nomorelaps.adapters.in.api.UserRequest;
import com.nomorelaps.adapters.in.api.UserResponse;
import com.nomorelaps.adapters.in.soap.interfaces.IAuthSoapService;
import com.nomorelaps.adapters.mapper.UserMapper;
import com.nomorelaps.business.interfaces.IUserService;
import com.nomorelaps.domain.models.User;
import com.nomorelaps.infrastructure.security.JwtService;
import jakarta.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Implementation of the Authentication SOAP Service.
 * Handles user login and registration processes.
 * 
 * @author nexphernandez
 */
@Service
@WebService(
    serviceName = "AuthSoapService",
    portName = "AuthSoapPort",
    targetNamespace = "http://soap.in.adapters.nomorelaps.com/",
    endpointInterface = "com.nomorelaps.adapters.in.soap.interfaces.IAuthSoapService"
)
public class AuthSoapService implements IAuthSoapService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final IUserService userService;
    private final UserMapper userMapper;

    @Autowired
    public AuthSoapService(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
                           JwtService jwtService, IUserService userService, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String jwt = jwtService.generateToken(userDetails);
        
        AuthResponse response = new AuthResponse();
        response.setToken(jwt);
        return response;
    }

    @Override
    public UserResponse register(UserRequest userRequest) {
        User user = userMapper.toDomainFromRequest(userRequest);
        User savedUser = userService.create(user);
        return userMapper.toResponse(savedUser);
    }
}

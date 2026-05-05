package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.UserResponse;
import com.nomorelaps.adapters.in.soap.interfaces.IUserSoapService;
import com.nomorelaps.adapters.mapper.UserMapper;
import com.nomorelaps.business.interfaces.IUserService;
import jakarta.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the User SOAP Service.
 * Manages user-related queries for SOAP clients.
 * 
 * @author nexphernandez
 */
@Service
@WebService(
    serviceName = "UserSoapService",
    portName = "UserSoapPort",
    targetNamespace = "http://soap.in.adapters.nomorelaps.com/",
    endpointInterface = "com.nomorelaps.adapters.in.soap.interfaces.IUserSoapService"
)
public class UserSoapService implements IUserSoapService {

    private final IUserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserSoapService(IUserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse findByEmail(String email) {
        return userService.findByEmail(email)
                .map(userMapper::toResponse)
                .orElse(null);
    }

    @Override
    public UserResponse findById(Long id) {
        return userService.findById(id)
                .map(userMapper::toResponse)
                .orElse(null);
    }

    @Override
    public List<UserResponse> findAll() {
        return userService.findAll().stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }
}

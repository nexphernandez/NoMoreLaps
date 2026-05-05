package com.nomorelaps.adapters.in.soap.interfaces;

import com.nomorelaps.adapters.in.api.AuthRequest;
import com.nomorelaps.adapters.in.api.AuthResponse;
import com.nomorelaps.adapters.in.api.UserRequest;
import com.nomorelaps.adapters.in.api.UserResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

/**
 * SOAP Service Interface for Authentication and Identity Management.
 * Handles credential verification and new user onboarding via XML messages.
 * 
 * @author nexphernandez
 */
@WebService(targetNamespace = "http://soap.in.adapters.nomorelaps.com/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface IAuthSoapService {

    /**
     * Authenticates a user and generates a security token for future requests.
     * 
     * @param authRequest Contains the user's email and password.
     * @return {@link AuthResponse} containing the JWT security token.
     */
    @WebMethod(operationName = "loginUser", action = "urn:LoginUser")
    @WebResult(name = "authResponse")
    AuthResponse login(@WebParam(name = "authRequest") AuthRequest authRequest);

    /**
     * Registers a new user account in the platform.
     * 
     * @param userRequest Contains the personal and account data for the new user.
     * @return {@link UserResponse} representing the newly created user profile.
     */
    @WebMethod(operationName = "registerUser", action = "urn:RegisterUser")
    @WebResult(name = "registrationDetail")
    UserResponse register(@WebParam(name = "userRequest") UserRequest userRequest);
}

package com.nomorelaps.adapters.in.soap.interfaces;

import com.nomorelaps.adapters.in.api.UserResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import java.util.List;

/**
 * SOAP Service Interface for User operations.
 * Allows managing user profiles and identity lookups via SOAP.
 * 
 * @author nexphernandez
 */
@WebService(targetNamespace = "http://soap.in.adapters.nomorelaps.com/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface IUserSoapService {

    /**
     * Finds a user profile by their registered email address.
     * 
     * @param email The unique email of the user.
     * @return The {@link UserResponse} if found, or null otherwise.
     */
    @WebMethod(operationName = "findUserByEmail", action = "urn:FindUserByEmail")
    @WebResult(name = "userProfile")
    UserResponse findByEmail(@WebParam(name = "email") String email);

    /**
     * Finds a user profile by their unique internal identifier.
     * 
     * @param id The unique database ID of the user.
     * @return The {@link UserResponse} if found, or null otherwise.
     */
    @WebMethod(operationName = "findUserById", action = "urn:FindUserById")
    @WebResult(name = "userProfile")
    UserResponse findById(@WebParam(name = "userId") Long id);

    /**
     * Retrieves all users registered in the platform (Admin usage).
     * 
     * @return A list of all {@link UserResponse}.
     */
    @WebMethod(operationName = "findAllUsers", action = "urn:FindAllUsers")
    @WebResult(name = "userList")
    List<UserResponse> findAll();
}

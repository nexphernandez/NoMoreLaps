package com.nomorelaps.adapters.in.soap.interfaces;

import com.nomorelaps.adapters.in.api.RoleResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import java.util.List;

/**
 * SOAP Service Interface for Role management.
 * Exposes system roles for access control information via SOAP.
 * 
 * @author nexphernandez
 */
@WebService(targetNamespace = "http://soap.in.adapters.nomorelaps.com/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface IRoleSoapService {

    /**
     * Lists all security roles defined in the system.
     * 
     * @return A list of {@link RoleResponse}.
     */
    @WebMethod(operationName = "findAllRoles", action = "urn:FindAllRoles")
    @WebResult(name = "roleList")
    List<RoleResponse> findAll();

    /**
     * Finds a role by its unique ID.
     * 
     * @param id The database ID of the role.
     * @return The {@link RoleResponse} if found.
     */
    @WebMethod(operationName = "findRoleById", action = "urn:FindRoleById")
    @WebResult(name = "roleDetail")
    RoleResponse findById(@WebParam(name = "roleId") Long id);
}

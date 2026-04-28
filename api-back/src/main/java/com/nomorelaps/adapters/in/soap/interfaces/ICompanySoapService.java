package com.nomorelaps.adapters.in.soap.interfaces;

import com.nomorelaps.adapters.in.api.CompanyResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import java.util.List;

/**
 * SOAP Service Interface for Company management.
 * Provides endpoints to retrieve information about corporate entities.
 * 
 * @author nexphernandez
 */
@WebService(targetNamespace = "http://soap.in.adapters.nomorelaps.com/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface ICompanySoapService {

    /**
     * Retrieves all companies registered in the system.
     * 
     * @return A list of {@link CompanyResponse}.
     */
    @WebMethod(operationName = "findAllCompanies", action = "urn:FindAllCompanies")
    @WebResult(name = "companyList")
    List<CompanyResponse> findAll();

    /**
     * Finds a specific company by its unique identifier.
     * 
     * @param id The database ID of the company.
     * @return The {@link CompanyResponse} if found, null otherwise.
     */
    @WebMethod(operationName = "findCompanyById", action = "urn:FindCompanyById")
    @WebResult(name = "companyDetail")
    CompanyResponse findById(@WebParam(name = "companyId") Long id);
}

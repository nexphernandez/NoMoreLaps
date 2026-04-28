package com.nomorelaps.adapters.in.soap.interfaces;

import com.nomorelaps.adapters.in.api.ParkingResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import java.util.List;

/**
 * SOAP Service Interface for Parking operations.
 * Exposes functionality to discover and manage parking facilities via XML-based
 * messages.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
@WebService(targetNamespace = "http://soap.in.adapters.nomorelaps.com/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface IParkingSoapService {

    /**
     * Retrieves a complete list of all parking facilities registered in the system.
     * 
     * @return A list of {@link ParkingResponse} containing parking details.
     */
    @WebMethod(operationName = "findAllParkings", action = "urn:FindAllParkings")
    @WebResult(name = "parkingList")
    List<ParkingResponse> findAll();

    /**
     * Searches for a specific parking facility using its unique identifier.
     * 
     * @param id The unique digital ID of the parking lot.
     * @return The {@link ParkingResponse} if found, or null otherwise.
     */
    @WebMethod(operationName = "findParkingById", action = "urn:FindParkingById")
    @WebResult(name = "parkingDetail")
    ParkingResponse findById(@WebParam(name = "parkingId") Long id);

    /**
     * Retrieves all parking lots that belong to a specific corporate entity.
     * 
     * @param companyId The unique identifier of the managing company.
     * @return A list of {@link ParkingResponse} associated with the company.
     */
    @WebMethod(operationName = "findParkingsByCompany", action = "urn:FindParkingsByCompany")
    @WebResult(name = "companyParkings")
    List<ParkingResponse> findByCompanyId(@WebParam(name = "companyId") Long companyId);

    /**
     * Searches for parking lots by name or address.
     * 
     * @param query The search text.
     * @return A list of matching {@link ParkingResponse}.
     */
    @WebMethod(operationName = "searchParkings", action = "urn:SearchParkings")
    @WebResult(name = "searchResults")
    List<ParkingResponse> search(@WebParam(name = "query") String query);

    /**
     * Finds parkings near a specific coordinate.
     * 
     * @param lat Latitude.
     * @param lng Longitude.
     * @param radius Maximum distance in KM.
     * @return A list of nearby {@link ParkingResponse}.
     */
    @WebMethod(operationName = "findNearbyParkings", action = "urn:FindNearbyParkings")
    @WebResult(name = "nearbyResults")
    List<ParkingResponse> findNearby(
            @WebParam(name = "latitude") double lat, 
            @WebParam(name = "longitude") double lng, 
            @WebParam(name = "radius") double radius);
}
        
            
            
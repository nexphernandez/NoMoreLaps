package com.nomorelaps.adapters.in.soap.interfaces;

import com.nomorelaps.adapters.in.api.ParkingSpotResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import java.util.List;

/**
 * SOAP Service Interface for Parking Spot operations.
 * Allows monitoring the availability of physical spaces within a parking lot.
 * 
 * @author nexphernandez
 */
@WebService(targetNamespace = "http://soap.in.adapters.nomorelaps.com/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface IParkingSpotSoapService {

    /**
     * Finds all spots associated with a specific parking facility.
     * 
     * @param parkingId The ID of the parent parking lot.
     * @return A list of {@link ParkingSpotResponse}.
     */
    @WebMethod(operationName = "findSpotsByParking", action = "urn:FindSpotsByParking")
    @WebResult(name = "spotList")
    List<ParkingSpotResponse> findByParkingId(@WebParam(name = "parkingId") Long parkingId);

    /**
     * Lists only the currently free spots in a parking facility.
     * 
     * @param parkingId The ID of the parking lot.
     * @return A list of unoccupied {@link ParkingSpotResponse}.
     */
    @WebMethod(operationName = "findAvailableSpotsByParking", action = "urn:FindAvailableSpotsByParking")
    @WebResult(name = "availableSpotList")
    List<ParkingSpotResponse> findAvailableSpots(@WebParam(name = "parkingId") Long parkingId);
}

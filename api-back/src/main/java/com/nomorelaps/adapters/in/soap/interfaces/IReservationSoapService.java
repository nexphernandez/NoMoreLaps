package com.nomorelaps.adapters.in.soap.interfaces;

import com.nomorelaps.adapters.in.api.ReservationResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import java.util.List;

/**
 * SOAP Service Interface for Reservation management.
 * Handles the retrieval of user booking history via SOAP.
 * 
 * @author nexphernandez
 */
@WebService(targetNamespace = "http://soap.in.adapters.nomorelaps.com/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface IReservationSoapService {

    /**
     * Retrieves all reservations performed by a specific user.
     * 
     * @param userId The unique identifier of the user.
     * @return A list of {@link ReservationResponse} objects.
     */
    @WebMethod(operationName = "findReservationsByUser", action = "urn:FindReservationsByUser")
    @WebResult(name = "reservationList")
    List<ReservationResponse> findByUserId(@WebParam(name = "userId") Long userId);

    /**
     * Retrieves all reservations for a specific parking spot.
     * 
     * @param spotId The ID of the spot.
     * @return A list of {@link ReservationResponse}.
     */
    @WebMethod(operationName = "findReservationsBySpot", action = "urn:FindReservationsBySpot")
    @WebResult(name = "spotReservations")
    List<ReservationResponse> findByParkingSpotId(@WebParam(name = "spotId") Long spotId);

    /**
     * Retrieves reservations filtered by their current state.
     * 
     * @param state The state (ACTIVA, CANCELADA, FINALIZADA).
     * @return A list of {@link ReservationResponse}.
     */
    @WebMethod(operationName = "findReservationsByState", action = "urn:FindReservationsByState")
    @WebResult(name = "stateReservations")
    List<ReservationResponse> findByState(@WebParam(name = "state") String state);
}

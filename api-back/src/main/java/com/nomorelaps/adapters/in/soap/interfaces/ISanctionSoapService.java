package com.nomorelaps.adapters.in.soap.interfaces;

import com.nomorelaps.adapters.in.api.SanctionResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import java.util.List;

/**
 * SOAP Service Interface for Sanction management.
 * Provides endpoints to check for penalties or fines applied to users.
 * 
 * @author nexphernandez
 */
@WebService(targetNamespace = "http://soap.in.adapters.nomorelaps.com/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface ISanctionSoapService {

    /**
     * Retrieves all sanctions applied to a specific user.
     * 
     * @param userId The ID of the user.
     * @return A list of {@link SanctionResponse} objects.
     */
    @WebMethod(operationName = "findSanctionsByUser", action = "urn:FindSanctionsByUser")
    @WebResult(name = "sanctionList")
    List<SanctionResponse> findByUserId(@WebParam(name = "userId") Long userId);

    /**
     * Retrieves all sanctions associated with a specific reservation.
     * 
     * @param reservationId The ID of the reservation.
     * @return A list of {@link SanctionResponse}.
     */
    @WebMethod(operationName = "findSanctionsByReservation", action = "urn:FindSanctionsByReservation")
    @WebResult(name = "reservationSanctions")
    List<SanctionResponse> findByReservationId(@WebParam(name = "reservationId") Long reservationId);

    /**
     * Marks a sanction as paid.
     * 
     * @param id The ID of the sanction.
     * @return The updated {@link SanctionResponse}.
     */
    @WebMethod(operationName = "paySanction", action = "urn:PaySanction")
    @WebResult(name = "paidSanction")
    SanctionResponse pay(@WebParam(name = "sanctionId") Long id);
}

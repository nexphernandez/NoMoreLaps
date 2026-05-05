package com.nomorelaps.adapters.in.soap.interfaces;

import com.nomorelaps.adapters.in.api.DynamicPriceResponse;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import java.util.List;

/**
 * SOAP Service Interface for Dynamic Pricing operations.
 * Exposes pricing algorithms and rates for parking slots via SOAP.
 * 
 * @author nexphernandez
 */
@WebService(targetNamespace = "http://soap.in.adapters.nomorelaps.com/")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface IDynamicPriceSoapService {

    /**
     * Lists all dynamic pricing configurations in the platform.
     * 
     * @return A list of {@link DynamicPriceResponse}.
     */
    @WebMethod(operationName = "findAllDynamicPrices", action = "urn:FindAllDynamicPrices")
    @WebResult(name = "dynamicPriceList")
    List<DynamicPriceResponse> findAll();

    /**
     * Finds a pricing configuration by its unique ID.
     * 
     * @param id The ID of the pricing record.
     * @return The {@link DynamicPriceResponse} if found.
     */
    @WebMethod(operationName = "findDynamicPriceById", action = "urn:FindDynamicPriceById")
    @WebResult(name = "dynamicPriceDetail")
    DynamicPriceResponse findById(@WebParam(name = "priceId") Long id);

    /**
     * Retrieves all pricing configurations associated with a specific parking lot.
     * 
     * @param parkingId The ID of the parking facility.
     * @return A list of {@link DynamicPriceResponse} for that parking.
     */
    @WebMethod(operationName = "findDynamicPricesByParking", action = "urn:FindDynamicPricesByParking")
    @WebResult(name = "parkingDynamicPrices")
    List<DynamicPriceResponse> findByParkingId(@WebParam(name = "parkingId") Long parkingId);
}

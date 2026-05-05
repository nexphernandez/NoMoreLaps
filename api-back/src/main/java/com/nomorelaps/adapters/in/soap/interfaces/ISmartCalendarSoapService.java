package com.nomorelaps.adapters.in.soap.interfaces;

import com.nomorelaps.adapters.in.api.SmartGeocodeRequest;
import com.nomorelaps.adapters.in.api.SmartGeocodeResponse;
import com.nomorelaps.adapters.in.api.SmartRecommendationRequest;
import com.nomorelaps.adapters.in.api.SmartRecommendationResponse;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

import static jakarta.jws.soap.SOAPBinding.Style.DOCUMENT;
import static jakarta.jws.soap.SOAPBinding.Use.LITERAL;

/**
 * SOAP Web Service interface for Smart Calendar operations.
 * Exposes geocoding and parking recommendation features.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@WebService(targetNamespace = "http://soap.nomorelaps.com/")
@SOAPBinding(style = DOCUMENT, use = LITERAL)
public interface ISmartCalendarSoapService {

    /**
     * Converts a destination query into coordinates.
     *
     * @param request structured geocode request.
     * @return geocode response with normalized address and coordinates.
     */
    @WebMethod(operationName = "geocode")
    @WebResult(name = "geocodeResponse")
    SmartGeocodeResponse geocode(
            @WebParam(name = "geocodeRequest") SmartGeocodeRequest request);

    /**
     * Returns parking recommendations for a destination and time window.
     *
     * @param request recommendation search criteria.
     * @return list of suggested parkings with available spots.
     */
    @WebMethod(operationName = "recommend")
    @WebResult(name = "recommendationResponse")
    SmartRecommendationResponse recommend(
            @WebParam(name = "recommendationRequest") SmartRecommendationRequest request);
}

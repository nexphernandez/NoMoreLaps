package com.nomorelaps.adapters.in.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomorelaps.adapters.in.api.SmartGeocodeRequest;
import com.nomorelaps.adapters.in.api.SmartGeocodeResponse;
import com.nomorelaps.adapters.in.api.SmartRecommendationRequest;
import com.nomorelaps.adapters.in.api.SmartRecommendationResponse;
import com.nomorelaps.adapters.in.soap.interfaces.ISmartCalendarSoapService;
import com.nomorelaps.business.SmartCalendarService;

import jakarta.jws.WebService;

/**
 * Implementation of the Smart Calendar SOAP Web Service.
 * Delegates execution to the business service layer.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@Service
@WebService(
        serviceName = "SmartCalendarSoapService",
        portName = "SmartCalendarSoapServicePort",
        targetNamespace = "http://soap.nomorelaps.com/",
        endpointInterface = "com.nomorelaps.adapters.in.soap.interfaces.ISmartCalendarSoapService")
public class SmartCalendarSoapService implements ISmartCalendarSoapService {

    private final SmartCalendarService smartCalendarService;

    @Autowired
    public SmartCalendarSoapService(SmartCalendarService smartCalendarService) {
        this.smartCalendarService = smartCalendarService;
    }

    @Override
    public SmartGeocodeResponse geocode(SmartGeocodeRequest request) {
        if (request == null || request.getQuery() == null) {
            throw new IllegalArgumentException("Geocode request and query must not be null.");
        }
        return smartCalendarService.geocode(request.getQuery());
    }

    @Override
    public SmartRecommendationResponse recommend(SmartRecommendationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Recommendation request must not be null.");
        }
        return smartCalendarService.recommend(request);
    }
}

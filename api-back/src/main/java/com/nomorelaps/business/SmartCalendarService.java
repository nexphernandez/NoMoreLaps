package com.nomorelaps.business;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nomorelaps.adapters.in.api.SmartGeocodeResponse;
import com.nomorelaps.adapters.in.api.SmartRecommendationItemResponse;
import com.nomorelaps.adapters.in.api.SmartRecommendationRequest;
import com.nomorelaps.adapters.in.api.SmartRecommendationResponse;
import com.nomorelaps.business.interfaces.IGeocodingProvider;
import com.nomorelaps.business.interfaces.IParkingService;
import com.nomorelaps.business.interfaces.IParkingSpotService;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.domain.models.ParkingSpot;

/**
 * Application service for smart-calendar use cases.
 * Handles geocoding and recommendation generation based on destination/time.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@Service
public class SmartCalendarService {
    private static final double EARTH_RADIUS_KM = 6371.0;
    private final IParkingService parkingService;
    private final IParkingSpotService parkingSpotService;
    private final IGeocodingProvider geocodingProvider;

    @Autowired
    public SmartCalendarService(IParkingService parkingService,IParkingSpotService parkingSpotService,IGeocodingProvider geocodingProvider) {
        this.parkingService = parkingService;
        this.parkingSpotService = parkingSpotService;
        this.geocodingProvider = geocodingProvider;
    }

    /**
     * Resolves a destination query into coordinates.
     * Delegates to the geocoding provider.
     *
     * @param query destination text.
     * @return normalized address and coordinates.
     */
    public SmartGeocodeResponse geocode(String query) {
        return geocodingProvider.geocode(query);
    }

    /**
     * Builds ordered parking suggestions for a requested destination and time
     * window.
     *
     * @param request recommendation request.
     * @return recommendations with available spots and suggested reservation
     *         window.
     */
    public SmartRecommendationResponse recommend(SmartRecommendationRequest request) {
        Double latitude = request.getLatitude();
        Double longitude = request.getLongitude();
        String destinationText = request.getDestinationText();
        if ((latitude == null || longitude == null) && destinationText != null && !destinationText.isBlank()) {
            SmartGeocodeResponse geocode = geocode(destinationText);
            latitude = geocode.getLatitude();
            longitude = geocode.getLongitude();
            destinationText = geocode.getFormattedAddress();
        }
        if (latitude == null || longitude == null) {
            throw new IllegalArgumentException("Destination coordinates are required.");
        }

        LocalDateTime startTime = LocalDateTime.parse(request.getStartTime());
        LocalDateTime endTime = startTime.plusHours(request.getDurationHours());
        List<Parking> nearby = parkingService.findNearby(latitude, longitude, request.getRadiusKm());
        List<SmartRecommendationItemResponse> items = new ArrayList<>();

        for (Parking parking : nearby) {
            List<ParkingSpot> spots = parkingSpotService.findAvailableSpots(parking.getId());
            if (spots.isEmpty()) {
                continue;
            }

            SmartRecommendationItemResponse item = new SmartRecommendationItemResponse(
                    parking.getId(),parking.getName(),parking.getAddress(),
                    distanceInKm(latitude, longitude, parking.getLatitude(), parking.getLongitude()),
                    spots.size(),spots.get(0).getId(),spots.get(0).getNumber(),
                    startTime.toString(),endTime.toString(),"Est. 2.00€/h");
            items.add(item);
        }

        items.sort(
                Comparator.comparing(SmartRecommendationItemResponse::getDistanceKm)
                        .thenComparing(SmartRecommendationItemResponse::getAvailableSpots, Comparator.reverseOrder()));

        return new SmartRecommendationResponse(destinationText, latitude, longitude, items);
    }

    /**
     * Computes great-circle distance between 2 geographic points.
     *
     * @param lat1 first latitude.
     * @param lng1 first longitude.
     * @param lat2 second latitude.
     * @param lng2 second longitude.
     * @return distance in kilometers.
     */
    private Double distanceInKm(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        return EARTH_RADIUS_KM * (2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)));
    }
}

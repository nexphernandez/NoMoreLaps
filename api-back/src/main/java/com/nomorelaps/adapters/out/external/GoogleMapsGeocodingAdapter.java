package com.nomorelaps.adapters.out.external;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nomorelaps.adapters.in.api.SmartGeocodeResponse;
import com.nomorelaps.business.interfaces.IGeocodingProvider;
import com.nomorelaps.business.interfaces.IParkingService;
import com.nomorelaps.domain.models.Parking;

/**
 * Adapter for Google Maps Geocoding API.
 * Implements the IGeocodingProvider output port.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@Component
public class GoogleMapsGeocodingAdapter implements IGeocodingProvider {

    private final RestTemplate restTemplate = new RestTemplate();
    private final IParkingService parkingService;

    @Value("${google.maps.api.key:}")
    private String googleMapsApiKey;

    @Autowired
    public GoogleMapsGeocodingAdapter(IParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @Override
    public SmartGeocodeResponse geocode(String query) {
        if (googleMapsApiKey == null || googleMapsApiKey.isBlank()) {
            return fallbackToLocal(query);
        }

        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = String.format(
                    "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s",
                    encodedQuery, googleMapsApiKey);

            Map<?, ?> response = restTemplate.getForObject(url, Map.class);
            if (response == null || !response.containsKey("results")) {
                return fallbackToLocal(query);
            }

            List<?> results = (List<?>) response.get("results");
            if (results.isEmpty()) {
                return fallbackToLocal(query);
            }

            Map<?, ?> first = (Map<?, ?>) results.get(0);
            String formattedAddress = String.valueOf(first.get("formatted_address"));
            Map<?, ?> geometry = (Map<?, ?>) first.get("geometry");
            Map<?, ?> location = (Map<?, ?>) geometry.get("location");
            Double lat = ((Number) location.get("lat")).doubleValue();
            Double lng = ((Number) location.get("lng")).doubleValue();

            return new SmartGeocodeResponse(formattedAddress, lat, lng);
        } catch (Exception e) {
            return fallbackToLocal(query);
        }
    }

    private SmartGeocodeResponse fallbackToLocal(String query) {
        List<Parking> fallback = parkingService.searchByNameOrAddress(query);
        if (fallback.isEmpty()) {
            throw new IllegalArgumentException("Destination could not be resolved locally or via API.");
        }
        Parking first = fallback.get(0);
        return new SmartGeocodeResponse(first.getAddress(), first.getLatitude(), first.getLongitude());
    }
}

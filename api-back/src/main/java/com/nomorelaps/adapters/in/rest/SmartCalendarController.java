package com.nomorelaps.adapters.in.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nomorelaps.adapters.in.api.SmartGeocodeRequest;
import com.nomorelaps.adapters.in.api.SmartGeocodeResponse;
import com.nomorelaps.adapters.in.api.SmartRecommendationRequest;
import com.nomorelaps.adapters.in.api.SmartRecommendationResponse;
import com.nomorelaps.business.SmartCalendarService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * REST Controller for Smart Calendar endpoints.
 * Exposes geocoding and smart recommendation operations for calendar events.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/smart-calendar")
@Tag(name = "Smart Calendar", description = "Calendar geocoding and smart parking recommendations")
public class SmartCalendarController {
    private final SmartCalendarService smartCalendarService;

    @Autowired
    public SmartCalendarController(SmartCalendarService smartCalendarService) {
        this.smartCalendarService = smartCalendarService;
    }

    /**
     * Converts a destination text into coordinates and normalized address.
     *
     * @param request payload containing destination query.
     * @return formatted address and coordinates.
     */
    @PostMapping("/geocode")
    @Operation(summary = "Geocode destination text", description = "Converts a destination query into coordinates.")
    public ResponseEntity<SmartGeocodeResponse> geocode(@Valid @RequestBody SmartGeocodeRequest request) {
        return ResponseEntity.ok(smartCalendarService.geocode(request.getQuery()));
    }

    /**
     * Returns parking recommendations for a calendar-like event window.
     *
     * @param request payload with destination, start time, duration and radius.
     * @return ordered nearby parkings with available spots.
     */
    @PostMapping("/recommendations")
    @Operation(summary = "Get parking recommendations for an event", description = "Returns nearby parkings with available spots.")
    public ResponseEntity<SmartRecommendationResponse> recommendations(
            @Valid @RequestBody SmartRecommendationRequest request) {
        return ResponseEntity.ok(smartCalendarService.recommend(request));
    }
}

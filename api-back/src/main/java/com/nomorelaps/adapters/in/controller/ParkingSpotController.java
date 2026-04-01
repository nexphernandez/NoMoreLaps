package com.nomorelaps.adapters.in.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nomorelaps.adapters.in.api.ParkingSpotRequest;
import com.nomorelaps.adapters.in.api.ParkingSpotResponse;
import com.nomorelaps.adapters.mapper.ParkingSpotMapper;
import com.nomorelaps.business.interfaces.IParkingSpotService;
import com.nomorelaps.domain.models.ParkingSpot;

import jakarta.validation.Valid;

/**
 * REST Controller for ParkingSpot endpoints.
 * Exposes CRUD operations for the ParkingSpot entity via the API layer.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/parking-spots")
public class ParkingSpotController {

    private final IParkingSpotService parkingSpotService;
    private final ParkingSpotMapper parkingSpotMapper;

    @Autowired
    public ParkingSpotController(IParkingSpotService parkingSpotService, ParkingSpotMapper parkingSpotMapper) {
        this.parkingSpotService = parkingSpotService;
        this.parkingSpotMapper = parkingSpotMapper;
    }

    /**
     * Creates a new parking spot.
     *
     * @param request The parking spot data from the API.
     * @return The created parking spot as a response DTO.
     */
    @PostMapping
    public ResponseEntity<ParkingSpotResponse> create(@Valid @RequestBody ParkingSpotRequest request) {
        ParkingSpot domain = parkingSpotMapper.toDomainFromRequest(request);
        ParkingSpot saved = parkingSpotService.create(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotMapper.toResponse(saved));
    }

    /**
     * Finds a parking spot by its unique identifier.
     *
     * @param id The parking spot ID.
     * @return The found parking spot or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpotResponse> findById(@PathVariable Long id) {
        return parkingSpotService.findById(id)
                .map(spot -> ResponseEntity.ok(parkingSpotMapper.toResponse(spot)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all parking spots for a specific parking lot.
     *
     * @param parkingId The parking lot ID.
     * @return A list of all spots in the parking.
     */
    @GetMapping("/parking/{parkingId}")
    public ResponseEntity<List<ParkingSpotResponse>> findByParkingId(@PathVariable Long parkingId) {
        List<ParkingSpotResponse> responses = parkingSpotService.findByParkingId(parkingId).stream()
                .map(parkingSpotMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves only the available (free) spots in a parking.
     *
     * @param parkingId The parking lot ID.
     * @return A list of available spots.
     */
    @GetMapping("/parking/{parkingId}/available")
    public ResponseEntity<List<ParkingSpotResponse>> findAvailableSpots(@PathVariable Long parkingId) {
        List<ParkingSpotResponse> responses = parkingSpotService.findAvailableSpots(parkingId).stream()
                .map(parkingSpotMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Updates an existing parking spot.
     *
     * @param id      The parking spot ID to update.
     * @param request The updated parking spot data.
     * @return The updated parking spot.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpotResponse> update(@PathVariable Long id, @Valid @RequestBody ParkingSpotRequest request) {
        ParkingSpot domain = parkingSpotMapper.toDomainFromRequest(request);
        domain.setId(id);
        ParkingSpot updated = parkingSpotService.update(domain);
        return ResponseEntity.ok(parkingSpotMapper.toResponse(updated));
    }

    /**
     * Deletes a parking spot by its ID.
     *
     * @param id The parking spot ID to delete.
     * @return 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        parkingSpotService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

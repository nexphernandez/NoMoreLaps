package com.nomorelaps.adapters.in.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nomorelaps.adapters.in.api.ParkingRequest;
import com.nomorelaps.adapters.in.api.ParkingResponse;
import com.nomorelaps.adapters.mapper.ParkingMapper;
import com.nomorelaps.business.interfaces.IParkingService;
import com.nomorelaps.domain.models.Parking;

import jakarta.validation.Valid;

/**
 * REST Controller for Parking endpoints.
 * Exposes CRUD operations for the Parking entity via the API layer.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/parkings")
public class ParkingController {

    private final IParkingService parkingService;
    private final ParkingMapper parkingMapper;

    @Autowired
    public ParkingController(IParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    /**
     * Creates a new parking lot in the system.
     *
     * @param request The parking data from the API.
     * @return The created parking as a response DTO.
     */
    @PostMapping
    public ResponseEntity<ParkingResponse> create(@Valid @RequestBody ParkingRequest request) {
        Parking domain = parkingMapper.toDomainFromRequest(request);
        Parking saved = parkingService.create(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingMapper.toResponse(saved));
    }

    /**
     * Finds a parking lot by its unique identifier.
     *
     * @param id The parking ID.
     * @return The found parking or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ParkingResponse> findById(@PathVariable Long id) {
        return parkingService.findById(id)
                .map(parking -> ResponseEntity.ok(parkingMapper.toResponse(parking)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all parking lots belonging to a specific company.
     *
     * @param companyId The company ID.
     * @return A list of parkings for the company.
     */
    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ParkingResponse>> findByCompanyId(@PathVariable Long companyId) {
        List<ParkingResponse> responses = parkingService.findAllByCompanyId(companyId).stream()
                .map(parkingMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Updates an existing parking lot.
     *
     * @param id      The parking ID to update.
     * @param request The updated parking data.
     * @return The updated parking.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ParkingResponse> update(@PathVariable Long id, @Valid @RequestBody ParkingRequest request) {
        Parking domain = parkingMapper.toDomainFromRequest(request);
        domain.setId(id);
        Parking updated = parkingService.update(domain);
        return ResponseEntity.ok(parkingMapper.toResponse(updated));
    }

    /**
     * Deletes a parking lot by its ID.
     *
     * @param id The parking ID to delete.
     * @return 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        parkingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

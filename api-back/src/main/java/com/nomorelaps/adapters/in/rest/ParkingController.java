package com.nomorelaps.adapters.in.rest;

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "Parking", description = "Operations related to parking facility management")
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
    @Operation(summary = "Create a new parking lot", description = "Adds a new physical parking location to a company's profile.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Parking created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
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
    @Operation(summary = "Find parking by ID", description = "Retrieves information about a specific parking lot.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parking found"),
            @ApiResponse(responseCode = "404", description = "Parking not found")
    })
    public ResponseEntity<ParkingResponse> findById(@PathVariable Long id) {
        return parkingService.findById(id)
                .map(parking -> ResponseEntity.ok(parkingMapper.toResponse(parking)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all parking lots in the system.
     *
     * @return A list of all parkings.
     */
    @GetMapping
    @Operation(summary = "Retrieve all parking lots", description = "Returns a complete list of all parking locations registered in the system.")
    @ApiResponse(responseCode = "200", description = "List of all parkings retrieved")
    public ResponseEntity<List<ParkingResponse>> findAll() {
        List<ParkingResponse> responses = parkingService.findAll().stream()
                .map(parkingMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves all parking lots belonging to a specific company.
     *
     * @param companyId The company ID.
     * @return A list of parkings for the company.
     */
    @GetMapping("/company/{companyId}")
    @Operation(summary = "Find parkings by Company", description = "Retrieves all parking locations belonging to a single company.")
    @ApiResponse(responseCode = "200", description = "List of company parkings retrieved")
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
    @Operation(summary = "Update a parking lot", description = "Updates coordinates, times, or name of an existing parking lot.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parking updated successfully"),
            @ApiResponse(responseCode = "404", description = "Parking not found")
    })
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
    @Operation(summary = "Delete a parking lot", description = "Removes a parking lot from the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Parking deleted"),
            @ApiResponse(responseCode = "404", description = "Parking not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        parkingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Searches for parking lots by name or address.
     *
     * @param query The search text.
     * @return A list of matching parkings.
     */
    @GetMapping("/search")
    @Operation(summary = "Search parkings", description = "Finds parkings by name or address containing the query string.")
    @ApiResponse(responseCode = "200", description = "Search results retrieved")
    public ResponseEntity<List<ParkingResponse>> search(@RequestParam String query) {
        List<ParkingResponse> responses = parkingService.searchByNameOrAddress(query).stream()
                .map(parkingMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Finds parkings near a specific coordinate.
     *
     * @param lat    Latitude of the center point.
     * @param lng    Longitude of the center point.
     * @param radius Maximum distance in kilometers.
     * @return A list of nearby parkings.
     */
    @GetMapping("/nearby")
    @Operation(summary = "Find nearby parkings", description = "Retrieves parkings within a certain distance from a location.")
    @ApiResponse(responseCode = "200", description = "Nearby parkings retrieved")
    public ResponseEntity<List<ParkingResponse>> findNearby(
            @RequestParam double lat, 
            @RequestParam double lng, 
            @RequestParam(defaultValue = "10.0") double radius) {
        List<ParkingResponse> responses = parkingService.findNearby(lat, lng, radius).stream()
                .map(parkingMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}

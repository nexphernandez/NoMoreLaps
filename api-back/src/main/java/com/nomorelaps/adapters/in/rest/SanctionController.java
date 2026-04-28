package com.nomorelaps.adapters.in.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nomorelaps.adapters.in.api.SanctionRequest;
import com.nomorelaps.adapters.in.api.SanctionResponse;
import com.nomorelaps.adapters.mapper.SanctionMapper;
import com.nomorelaps.business.interfaces.ISanctionService;
import com.nomorelaps.domain.models.Sanction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

/**
 * REST Controller for Sanction endpoints.
 * Exposes CRUD operations for the Sanction entity via the API layer.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/sanctions")
@Tag(name = "Sanction", description = "Operations related to penalty and sanction management")
public class SanctionController {

    private final ISanctionService sanctionService;
    private final SanctionMapper sanctionMapper;

    @Autowired
    public SanctionController(ISanctionService sanctionService, SanctionMapper sanctionMapper) {
        this.sanctionService = sanctionService;
        this.sanctionMapper = sanctionMapper;
    }

    /**
     * Creates a new sanction.
     *
     * @param request The sanction data from the API.
     * @return The created sanction as a response DTO.
     */
    @PostMapping
    @Operation(summary = "Create a sanction", description = "Applies a new penalty or sanction to a user for a specific reservation.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sanction created"),
            @ApiResponse(responseCode = "400", description = "Invalid sanction data")
    })
    public ResponseEntity<SanctionResponse> create(@Valid @RequestBody SanctionRequest request) {
        Sanction domain = sanctionMapper.toDomainFromRequest(request);
        Sanction saved = sanctionService.create(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(sanctionMapper.toResponse(saved));
    }

    /**
     * Finds a sanction by its unique identifier.
     *
     * @param id The sanction ID.
     * @return The found sanction or 404 if not found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Find sanction by ID", description = "Retrieves information about a specific sanction.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found"),
            @ApiResponse(responseCode = "404", description = "Sanction not found")
    })
    public ResponseEntity<SanctionResponse> findById(@PathVariable Long id) {
        return sanctionService.findById(id)
                .map(s -> ResponseEntity.ok(sanctionMapper.toResponse(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all sanctions for a specific user.
     *
     * @param userId The user ID.
     * @return A list of sanctions for the user.
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Find sanctions by User", description = "Lists all sanctions applied to a specific user.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public ResponseEntity<List<SanctionResponse>> findByUserId(@PathVariable Long userId) {
        List<SanctionResponse> responses = sanctionService.findByUserId(userId).stream()
                .map(sanctionMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves all sanctions for a specific reservation.
     *
     * @param reservationId The reservation ID.
     * @return A list of sanctions for the reservation.
     */
    @GetMapping("/reservation/{reservationId}")
    @Operation(summary = "Find sanctions by Reservation", description = "Lists all sanctions associated with a specific booking.")
    @ApiResponse(responseCode = "200", description = "List retrieved successfully")
    public ResponseEntity<List<SanctionResponse>> findByReservationId(@PathVariable Long reservationId) {
        List<SanctionResponse> responses = sanctionService.findByReservationId(reservationId).stream()
                .map(sanctionMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Updates an existing sanction.
     *
     * @param id      The sanction ID to update.
     * @param request The updated sanction data.
     * @return The updated sanction.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a sanction", description = "Updates details or status of an existing sanction.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "404", description = "Sanction not found")
    })
    public ResponseEntity<SanctionResponse> update(@PathVariable Long id, @Valid @RequestBody SanctionRequest request) {
        Sanction domain = sanctionMapper.toDomainFromRequest(request);
        domain.setId(id);
        Sanction updated = sanctionService.update(domain);
        return ResponseEntity.ok(sanctionMapper.toResponse(updated));
    }

    /**
     * Deletes a sanction by its ID.
     *
     * @param id The sanction ID to delete.
     * @return 204 No Content.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a sanction", description = "Removes a sanction record from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Sanction not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sanctionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Marks a sanction as paid.
     *
     * @param id The ID of the sanction to pay.
     * @return The updated sanction.
     */
    @PatchMapping("/{id}/pay")
    @Operation(summary = "Pay a sanction", description = "Marks a specific penalty as paid by the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paid successfully"),
            @ApiResponse(responseCode = "404", description = "Sanction not found")
    })
    public ResponseEntity<SanctionResponse> pay(@PathVariable Long id) {
        Sanction updated = sanctionService.paySanction(id);
        return ResponseEntity.ok(sanctionMapper.toResponse(updated));
    }
}

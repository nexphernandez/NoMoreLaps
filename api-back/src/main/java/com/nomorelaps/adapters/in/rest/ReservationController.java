package com.nomorelaps.adapters.in.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nomorelaps.adapters.in.api.ReservationRequest;
import com.nomorelaps.adapters.in.api.ReservationResponse;
import com.nomorelaps.adapters.mapper.ReservationMapper;
import com.nomorelaps.business.interfaces.IReservationService;
import com.nomorelaps.domain.models.Reservation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

/**
 * REST Controller for Reservation endpoints.
 * Exposes CRUD operations for the Reservation entity via the API layer.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/reservations")
@Tag(name = "Reservation", description = "Operations related to booking and reservation management")
public class ReservationController {

    private final IReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @Autowired
    public ReservationController(IReservationService reservationService, ReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
    }

    /**
     * Creates a new reservation in the system.
     *
     * @param request The reservation data from the API.
     * @return The created reservation as a response DTO.
     */
    @PostMapping
    @Operation(summary = "Create a reservation", description = "Places a new booking for a parking spot.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation created"),
            @ApiResponse(responseCode = "400", description = "Invalid reservation data"),
            @ApiResponse(responseCode = "409", description = "Spot already occupied for the requested time")
    })
    public ResponseEntity<ReservationResponse> create(@Valid @RequestBody ReservationRequest request) {
        Reservation domain = reservationMapper.toDomainFromRequest(request);
        Reservation saved = reservationService.create(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationMapper.toResponse(saved));
    }

    /**
     * Finds a reservation by its unique identifier.
     *
     * @param id The reservation ID.
     * @return The found reservation or 404 if not found.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Find reservation by ID", description = "Retrieves details of a specific booking.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity<ReservationResponse> findById(@PathVariable Long id) {
        return reservationService.findById(id)
                .map(r -> ResponseEntity.ok(reservationMapper.toResponse(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all reservations for a specific user.
     *
     * @param userId The user ID.
     * @return A list of reservations for the user.
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Find reservations by User", description = "Lists all bookings made by a particular user.")
    @ApiResponse(responseCode = "200", description = "List retrieved")
    public ResponseEntity<List<ReservationResponse>> findByUserId(@PathVariable Long userId) {
        List<ReservationResponse> responses = reservationService.findByUserId(userId).stream()
                .map(reservationMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves all reservations for a specific company.
     *
     * @param companyId The company ID.
     * @return A list of reservations for the company.
     */
    @GetMapping("/company/{companyId}")
    @Operation(summary = "Find reservations by Company", description = "Lists all bookings for all parkings owned by a company.")
    @ApiResponse(responseCode = "200", description = "List retrieved")
    public ResponseEntity<List<ReservationResponse>> findByCompanyId(@PathVariable Long companyId) {
        List<ReservationResponse> responses = reservationService.findByCompanyId(companyId).stream()
                .map(reservationMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves all reservations for a specific parking spot.
     *
     * @param spotId The parking spot ID.
     * @return A list of reservations for the spot.
     */
    @GetMapping("/spot/{spotId}/occupied")
    @Operation(summary = "Get occupied slots for a spot", description = "Retrieves all active reservations for a spot to determine availability.")
    public ResponseEntity<List<ReservationResponse>> getOccupiedHours(@PathVariable Long spotId) {
        List<ReservationResponse> responses = reservationService.findByParkingSpotId(spotId).stream()
                .filter(r -> "ACTIVE".equals(r.getState()))
                .map(reservationMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves all active reservations for all spots in a parking.
     *
     * @param parkingId The parking ID.
     * @return A list of active reservations for that parking.
     */
    @GetMapping("/parking/{parkingId}/occupied")
    @Operation(summary = "Get all active reservations for a parking", description = "Retrieves all active bookings for any spot in the facility.")
    public ResponseEntity<List<ReservationResponse>> getOccupiedByParking(@PathVariable Long parkingId) {
        List<ReservationResponse> responses = reservationService.findByParkingId(parkingId).stream()
                .filter(r -> "ACTIVE".equals(r.getState()))
                .map(reservationMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves all reservations by state.
     *
     * @param state The state to filter by (ACTIVE, CANCELLED, COMPLETED).
     * @return A list of reservations matching the state.
     */
    @GetMapping("/state/{state}")
    @Operation(summary = "Filter reservations by state", description = "Retrieves bookings based on their status (ACTIVE, CANCELLED, etc.).")
    @ApiResponse(responseCode = "200", description = "List retrieved")
    public ResponseEntity<List<ReservationResponse>> findByState(@PathVariable String state) {
        List<ReservationResponse> responses = reservationService.findByState(state).stream()
                .map(reservationMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Updates an existing reservation.
     *
     * @param id      The reservation ID to update.
     * @param request The updated reservation data.
     * @return The updated reservation.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a reservation", description = "Modifies times or status of an existing booking.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity<ReservationResponse> update(@PathVariable Long id, @Valid @RequestBody ReservationRequest request) {
        Reservation domain = reservationMapper.toDomainFromRequest(request);
        domain.setId(id);
        Reservation updated = reservationService.update(domain);
        return ResponseEntity.ok(reservationMapper.toResponse(updated));
    }

    /**
     * Deletes a reservation by its ID.
     *
     * @param id The reservation ID to delete.
     * @return 204 No Content.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a reservation", description = "Removes a booking entry from the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates the payment status of a reservation.
     *
     * @param id   The reservation ID.
     * @param paid The new payment status.
     * @return The updated reservation.
     */
    @PatchMapping("/{id}/payment-status")
    @Operation(summary = "Update payment status", description = "Marks a reservation as paid or pending.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment status updated successfully"),
            @ApiResponse(responseCode = "404", description = "Reservation not found")
    })
    public ResponseEntity<ReservationResponse> updatePaymentStatus(
            @PathVariable Long id,
            @RequestParam boolean paid) {
        Reservation updated = reservationService.updatePaymentStatus(id, paid);
        return ResponseEntity.ok(reservationMapper.toResponse(updated));
    }
}

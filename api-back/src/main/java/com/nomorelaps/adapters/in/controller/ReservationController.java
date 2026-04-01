package com.nomorelaps.adapters.in.controller;

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
    public ResponseEntity<List<ReservationResponse>> findByUserId(@PathVariable Long userId) {
        List<ReservationResponse> responses = reservationService.findByUserId(userId).stream()
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
    @GetMapping("/spot/{spotId}")
    public ResponseEntity<List<ReservationResponse>> findByParkingSpotId(@PathVariable Long spotId) {
        List<ReservationResponse> responses = reservationService.findByParkingSpotId(spotId).stream()
                .map(reservationMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves all reservations by state.
     *
     * @param state The state to filter by (ACTIVA, CANCELADA, FINALIZADA).
     * @return A list of reservations matching the state.
     */
    @GetMapping("/state/{state}")
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
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

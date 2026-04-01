package com.nomorelaps.adapters.in.api;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for creating or updating a Reservation.
 * Encapsulates the necessary request data sent by the client.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class ReservationRequest {
    
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @NotNull(message = "El precio es obligatorio")
    private double price;
    @NotBlank(message = "El estado no puede estar vacío")
    private String state;
    @NotNull(message = "El ID de usuario es obligatorio")
    private Long userId;
    @NotNull(message = "El ID de plaza es obligatorio")
    private Long parkingSpotId;

    /**
     * Empty constructor 
     */
    public ReservationRequest() {
    }

    /**
     * Constructor with all the reservation attributes
     * @param startTime of the reservation
     * @param endTime of the reservation 
     * @param price of the reservation
     * @param state of the reservation
     * @param userId user id
     * @param parkingSpotId parking spot id
     */
    public ReservationRequest( LocalDateTime startTime, LocalDateTime endTime, 
    double price, String state, Long userId, Long parkingSpotId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.state = state;
        this.userId = userId;
        this.parkingSpotId = parkingSpotId;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getParkingSpotId() {
        return parkingSpotId;
    }

    public void setParkingSpotId(Long parkingSpotId) {
        this.parkingSpotId = parkingSpotId;
    }

}

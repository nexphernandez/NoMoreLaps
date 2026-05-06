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
    @NotNull(message = "Price is mandatory")
    private Double totalPrice;
    @NotBlank(message = "Status cannot be empty")
    private String status;
    @NotNull(message = "User ID is mandatory")
    private Long userId;
    @NotNull(message = "Spot ID is mandatory")
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
     * @param totalPrice of the reservation
     * @param status of the reservation
     * @param userId user id
     * @param parkingSpotId parking spot id
     */
    public ReservationRequest( LocalDateTime startTime, LocalDateTime endTime, 
    Double totalPrice, String status, Long userId, Long parkingSpotId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
        this.status = status;
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

    public Double getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
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

package com.nomorelaps.adapters.in.api;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) representing a Reservation in API responses.
 * Encapsulates the structured data sent back to the client.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class ReservationResponse {
    
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double price;
    private String state;
    private LocalDateTime creationTime;

    /**
     * Empty constructor
     */
    public ReservationResponse() {
    }

    /**
     * Constructor with the reservation primary key
     * @param id reservation id
     */
    public ReservationResponse(Long id) {
        this.id = id;
    }

    /**
     * Constructor with all the reservation attributes
     * @param id reservation 
     * @param startTime of the reservation
     * @param departureTime of the reservation 
     * @param price of the reservation
     * @param state of the reservation
     * @param creationTime of the reservation
     */
    public ReservationResponse(Long id, LocalDateTime startTime, LocalDateTime endTime, double price, String state, LocalDateTime creationTime) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.state = state;
        this.creationTime = creationTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getstate() {
        return this.state;
    }

    public void setstate(String state) {
        this.state = state;
    }

    public LocalDateTime getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ReservationResponse)) {
            return false;
        }
        ReservationResponse reservationResponse = (ReservationResponse) o;
        return Objects.equals(id, reservationResponse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

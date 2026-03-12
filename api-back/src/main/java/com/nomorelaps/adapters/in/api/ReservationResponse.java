package com.nomorelaps.adapters.in.api;

import java.time.LocalDateTime;
import java.util.Objects;

public class ReservationResponse {
    
    private Long id;
    private LocalDateTime starTime;
    private LocalDateTime endTime;
    private double price;
    private String State;
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
     * @param starTime of the reservation
     * @param departureTime of the reservation 
     * @param price of the reservation
     * @param State of the reservation
     * @param creationTime of the reservation
     */
    public ReservationResponse(Long id, LocalDateTime starTime, LocalDateTime endTime, double price, String State, LocalDateTime creationTime) {
        this.id = id;
        this.starTime = starTime;
        this.endTime = endTime;
        this.price = price;
        this.State = State;
        this.creationTime = creationTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStarTime() {
        return this.starTime;
    }

    public void setStarTime(LocalDateTime arrivalTime) {
        this.starTime = arrivalTime;
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
        return this.State;
    }

    public void setState(String State) {
        this.State = State;
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

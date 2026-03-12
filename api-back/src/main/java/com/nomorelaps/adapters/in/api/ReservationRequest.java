package com.nomorelaps.adapters.in.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


public class ReservationRequest {
    
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double price;
    private String State;
    private LocalDateTime creationTime;

    /**
     * Empty constructor 
     */
    public ReservationRequest() {
    }

    /**
     * Constructor with the reservation primary key
     * @param id reservation id
     */
    public ReservationRequest(Long id) {
        this.id = id;
    }

    /**
     * Constructor with all the reservation attributes
     * @param id reservation 
     * @param startTime of the reservation
     * @param endTime of the reservation 
     * @param price of the reservation
     * @param State of the reservation
     * @param creationTime of the reservation
     */
    public ReservationRequest(Long id, LocalDateTime startTime, LocalDateTime endTime, double price, String State, LocalDateTime creationTime) {
        this.id = id;
        this.startTime = startTime;
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
        if (!(o instanceof ReservationRequest)) {
            return false;
        }
        ReservationRequest reservationRequest = (ReservationRequest) o;
        return Objects.equals(id, reservationRequest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

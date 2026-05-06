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
    private Long parkingSpotId;
    private Long userId;
    private String parkingName;
    private String userName;

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
     */
    public ReservationResponse(Long id, LocalDateTime startTime, LocalDateTime endTime, double price, String state, LocalDateTime creationTime, Long parkingSpotId, Long userId, String parkingName, String userName) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.state = state;
        this.creationTime = creationTime;
        this.parkingSpotId = parkingSpotId;
        this.userId = userId;
        this.parkingName = parkingName;
        this.userName = userName;
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
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getParkingSpotId() {
        return parkingSpotId;
    }

    public void setParkingSpotId(Long parkingSpotId) {
        this.parkingSpotId = parkingSpotId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

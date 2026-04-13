package com.nomorelaps.domain.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Domain model representing a Reservation.
 * Contains the pure business logic and attributes, independent of databases or
 * APIs.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class Reservation {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double price;
    private String state;
    private LocalDateTime creationTime;
    private ParkingSpot parkingSpot;
    private User user;
    private Set<Sanction> sanctions = new HashSet<>();

    /**
     * Empty constructor
     */
    public Reservation() {
    }

    /**
     * Constructor with all attributes
     * 
     * @param id           reservation identifier
     * @param startTime    reservation start local time
     * @param endTime      reservation end local time
     * @param price        base price calculated
     * @param state        active, cancelled, etc
     * @param creationTime audit generated time
     * @param parkingSpot  mapped spot target
     * @param user         creator user
     * @param sanctions    list of associated possible sanctions
     */
    public Reservation(Long id, LocalDateTime startTime, LocalDateTime endTime, double price, String state,
            LocalDateTime creationTime, ParkingSpot parkingSpot, User user, Set<Sanction> sanctions) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.state = state;
        this.creationTime = creationTime;
        this.parkingSpot = parkingSpot;
        this.user = user;
        this.sanctions = sanctions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Sanction> getSanctions() {
        return sanctions;
    }

    public void setSanctions(Set<Sanction> sanctions) {
        this.sanctions = sanctions;
    }
}

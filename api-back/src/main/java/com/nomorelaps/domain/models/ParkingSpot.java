package com.nomorelaps.domain.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain model representing a Parking Spot.
 * Contains the pure business logic and attributes, independent of databases or
 * APIs.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class ParkingSpot {
    private Long id;
    private boolean state;
    private int number;
    private LocalDateTime registerDate;
    private Parking parking;

    /**
     * Empty constructor
     */
    public ParkingSpot() {
    }

    /**
     * Constructor with the parking spot primary key
     * @param id parking spot id
     */
    public ParkingSpot(Long id) {
        this.id = id;
    }

    /**
     * Constructor with all attributes
     * 
     * @param id           parking spot unique id
     * @param state        occupied or free state
     * @param number       specific number in parking
     * @param registerDate audit date of registration
     * @param parking      parent parking facility
     */
    public ParkingSpot(Long id, boolean state, int number, LocalDateTime registerDate, Parking parking) {
        this.id = id;
        this.state = state;
        this.number = number;
        this.registerDate = registerDate;
        this.parking = parking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public LocalDateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public Parking getParking() {
        return parking;
    }

    public void setParking(Parking parking) {
        this.parking = parking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpot that = (ParkingSpot) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

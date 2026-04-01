package com.nomorelaps.adapters.in.api;

import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object (DTO) for creating or updating a Parking Spot.
 * Encapsulates the necessary request data sent by the client.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class ParkingSpotRequest {
    
    private boolean state;

    @Positive(message = "El número de plaza debe ser mayor que cero")
    private int number;

    /**
     * Empty constructor
     */
    public ParkingSpotRequest() {
    }

    /**
     * Constructor with all the parameters
     * @param state of Parking Spot
     * @param number of Parking Spot
     */
    public ParkingSpotRequest(boolean state, int number) {
        this.state = state;
        this.number = number;
    }

    public boolean isState() {
        return this.state;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

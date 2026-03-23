package com.nomorelaps.adapters.in.api;

import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingSpotResponse {

    private Long id;

    private boolean state;

    private int number;

    private LocalDateTime registerDate;

    /**
     * Empty constructor
     */
    public ParkingSpotResponse() {
    }

    /**
     * Constructor with only the id
     * @param id of Parking Spot 
     */
    public ParkingSpotResponse(Long id){
        this.id=id;
    }

    /**
     * Constructor with all the parameters
     * @param id of Parking Spot
     * @param state of Parking Spot
     * @param number of Parking Spot
     * @param registerDate of Parking Spot
     */
    public ParkingSpotResponse(Long id, boolean state, int number, LocalDateTime registerDate) {
        this.id = id;
        this.state = state;
        this.number = number;
        this.registerDate = registerDate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getRegisterDate() {
        return this.registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ParkingSpotResponse)) {
            return false;
        }
        ParkingSpotResponse parkingSpotResponse = (ParkingSpotResponse) o;
        return Objects.equals(id, parkingSpotResponse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
}

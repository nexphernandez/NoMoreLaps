package com.nomorelaps.adapters.in.api;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) representing a Parking in API responses.
 * Encapsulates the structured data sent back to the client.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class ParkingResponse {

    private Long id;
    private String address;
    private String name;
    private Double latitude;
    private Double longitude;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private LocalDateTime createdAt;
    private Double sanctionAmount;
    private Integer sanctionIntervalInMinutes;
    private Double pricePerHour;
    
    /**
     * Empty constructor
     */
    public ParkingResponse() {
    }

    /**
     * Constructor with the parking ID
     * @param id of the parking
     */
    public ParkingResponse(Long id) {
        this.id = id;
    }

    /**
     * Constructor with all parameters of the Parking
     * @param id of the parking
     * @param address of the parking
     * @param name of the parking
     * @param latitude of the parking
     * @param longitude of the parking
     * @param openingTime of the parking that day
     * @param closingTime of the parking that day
     * @param createdAt of the parking creation day
     */
    public ParkingResponse(Long id, String address, String name, Double latitude, Double longitude, 
                           LocalDateTime openingTime, LocalDateTime closingTime, LocalDateTime createdAt, Double pricePerHour) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.createdAt = createdAt;
        this.pricePerHour = pricePerHour;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getOpeningTime() {
        return this.openingTime;
    }

    public void setOpeningTime(LocalDateTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalDateTime getClosingTime() {
        return this.closingTime;
    }

    public void setClosingTime(LocalDateTime closingTime) {
        this.closingTime = closingTime;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Double getSanctionAmount() {
        return sanctionAmount;
    }

    public void setSanctionAmount(Double sanctionAmount) {
        this.sanctionAmount = sanctionAmount;
    }

    public Integer getSanctionIntervalInMinutes() {
        return sanctionIntervalInMinutes;
    }

    public void setSanctionIntervalInMinutes(Integer sanctionIntervalInMinutes) {
        this.sanctionIntervalInMinutes = sanctionIntervalInMinutes;
    }

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ParkingResponse)) {
            return false;
        }
        ParkingResponse parkingResponse = (ParkingResponse) o;
        return Objects.equals(id, parkingResponse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
  
}

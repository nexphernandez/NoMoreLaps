package com.nomorelaps.adapters.in.api;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for creating or updating a Parking.
 * Encapsulates the necessary request data sent by the client.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class ParkingRequest {

    @NotBlank(message = "Address cannot be empty")
    private String address;
    
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "Latitude is mandatory")
    private Double latitude;
    @NotNull(message = "Longitude is mandatory")
    private Double longitude;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private Double pricePerHour;
    private Long companyId;
    private Double sanctionAmount;
    private Integer sanctionIntervalInMinutes;

    /**
     * Empty constructor
     */
    public ParkingRequest() {
    }

    /**
     * Constructor with all parameters of the Parking
     * @param address of the parking
     * @param name of the parking
     * @param latitude of the parking
     * @param longitude of the parking
     * @param openingTime of the parking that day
     * @param closingTime of the parking that day
     * @param pricePerHour price per hour
     * @param companyId company owner id
     * @param sanctionAmount sanction amount
     * @param sanctionInterval sanction interval
     */
    public ParkingRequest(String address, String name, Double latitude, Double longitude, 
                          LocalDateTime openingTime, LocalDateTime closingTime, Double pricePerHour,
                          Long companyId, Double sanctionAmount, Integer sanctionInterval) {
        this.address = address;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.pricePerHour = pricePerHour;
        this.companyId = companyId;
        this.sanctionAmount = sanctionAmount;
        this.sanctionIntervalInMinutes = sanctionInterval;
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

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

}

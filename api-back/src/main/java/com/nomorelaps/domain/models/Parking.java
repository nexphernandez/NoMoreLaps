package com.nomorelaps.domain.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Domain model representing a Parking location facility.
 * Contains the pure business logic and attributes, independent of databases or
 * APIs.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class Parking {
    private Long id;
    private String address;
    private String name;
    private Double latitude;
    private Double longitude;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private LocalDateTime createdAt;
    private Company company;
    private Set<ParkingSpot> parkingSpots = new HashSet<>();
    private Set<DynamicPrice> dynamicPrice = new HashSet<>();

    /**
     * Empty constructor
     */
    public Parking() {
    }

    /**
     * Constructor with all parameters
     * 
     * @param id           parking unique id
     * @param address      street direction
     * @param name         commercial name
     * @param latitude     gps latitude
     * @param longitude    gps longitude
     * @param openingTime  standard opening daily hours
     * @param closingTime  standard closing daily hours
     * @param createdAt    backend tracking integration date
     * @param company      landlord or tenant company
     * @param parkingSpots available physical spots inside
     * @param dynamicPrice pricing algorithm configurations
     */
    public Parking(Long id, String address, String name, Double latitude, Double longitude, LocalDateTime openingTime,
            LocalDateTime closingTime, LocalDateTime createdAt, Company company, Set<ParkingSpot> parkingSpots,
            Set<DynamicPrice> dynamicPrice) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.createdAt = createdAt;
        this.company = company;
        this.parkingSpots = parkingSpots;
        this.dynamicPrice = dynamicPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalDateTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalDateTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalDateTime closingTime) {
        this.closingTime = closingTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(Set<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public Set<DynamicPrice> getDynamicPrice() {
        return dynamicPrice;
    }

    public void setDynamicPrice(Set<DynamicPrice> dynamicPrice) {
        this.dynamicPrice = dynamicPrice;
    }
}

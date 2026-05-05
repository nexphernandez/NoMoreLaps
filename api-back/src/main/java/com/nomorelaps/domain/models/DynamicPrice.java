package com.nomorelaps.domain.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain model representing a Dynamic Price rule.
 * Contains the pure business logic and attributes, independent of databases or
 * APIs.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class DynamicPrice {
    private Long id;
    private int dayOfWeek;
    private String startHour;
    private String endHour;
    private double minPrice;
    private double maxPrice;
    private LocalDateTime createAt;
    private Parking parking;

    /**
     * Empty constructor
     */
    public DynamicPrice() {
    }

    /**
     * Constructor with the dynamic price primary key
     * @param id dynamic price id
     */
    public DynamicPrice(Long id) {
        this.id = id;
    }

    /**
     * Constructor with all attributes
     * 
     * @param id        dynamic price unique id
     * @param dayOfWeek day of the week mapped 1 to 7
     * @param startHour start time format
     * @param endHour   end time format
     * @param minPrice  minimum price allowed
     * @param maxPrice  maximum price allowed
     * @param createAt  backend creation audit date
     * @param parking   associated parking
     */
    public DynamicPrice(Long id, int dayOfWeek, String startHour, String endHour, double minPrice, double maxPrice,
            LocalDateTime createAt, Parking parking) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startHour = startHour;
        this.endHour = endHour;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.createAt = createAt;
        this.parking = parking;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
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
        DynamicPrice that = (DynamicPrice) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

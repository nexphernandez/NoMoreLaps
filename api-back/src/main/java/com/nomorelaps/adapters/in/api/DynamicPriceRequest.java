package com.nomorelaps.adapters.in.api;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object (DTO) for creating or updating a Dynamic Price.
 * Encapsulates the necessary request data sent by the client.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class DynamicPriceRequest {

    @Min(value = 1, message = "Day must be greater than or equal to 1 (Monday)")
    @Max(value = 7, message = "Day must be less than or equal to 7 (Sunday)")
    private Integer dayOfWeek;

    @NotBlank(message = "Start time is mandatory")
    private String startTime;

    @NotBlank(message = "End time is mandatory")
    private String endTime;

    @Positive(message = "Minimum price must be positive")
    private Double minPrice;

    @Positive(message = "Maximum price must be positive")
    private double maxPrice;

    /**
     * Empty constructor
     */
    public DynamicPriceRequest() {
    }

    /**
     * Constructor with all the parameters
     * 
     * @param dayOfWeek of Dynamic Price
     * @param startTime of Dynamic Price
     * @param endTime   of Dynamic Price
     * @param minPrice  of Dynamic Price
     * @param maxPrice  of Dynamic Price
     */
    public DynamicPriceRequest(int dayOfWeek, String startTime, String endTime, double minPrice, double maxPrice) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public int getDayOfWeek() {
        return this.dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStartHour() {
        return this.startTime;
    }

    public void setStartHour(String startTime) {
        this.startTime = startTime;
    }

    public String getEndHour() {
        return this.endTime;
    }

    public void setEndHour(String endTime) {
        this.endTime = endTime;
    }

    public double getMinPrice() {
        return this.minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return this.maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

}

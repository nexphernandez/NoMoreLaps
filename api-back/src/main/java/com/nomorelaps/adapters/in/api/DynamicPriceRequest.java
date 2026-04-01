package com.nomorelaps.adapters.in.api;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class DynamicPriceRequest {

    @Min(value = 1, message = "El día debe ser mayor o igual a 1 (Lunes)")
    @Max(value = 7, message = "El día debe ser menor o igual a 7 (Domingo)")
    private int dayOfWeek;

    @NotBlank(message = "La hora de inicio es obligatoria")
    private String startHour;

    @NotBlank(message = "La hora de fin es obligatoria")
    private String endHour;

    @Positive(message = "El precio mínimo debe ser positivo")
    private double minPrice;

    @Positive(message = "El precio máximo debe ser positivo")
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
     * @param startHour of Dynamic Price
     * @param endHour   of Dynamic Price
     * @param minPrice  of Dynamic Price
     * @param maxPrice  of Dynamic Price
     */
    public DynamicPriceRequest(int dayOfWeek, String startHour, String endHour, double minPrice, double maxPrice) {
        this.dayOfWeek = dayOfWeek;
        this.startHour = startHour;
        this.endHour = endHour;
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
        return this.startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return this.endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
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

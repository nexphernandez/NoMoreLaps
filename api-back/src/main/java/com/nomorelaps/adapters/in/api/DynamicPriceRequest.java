package com.nomorelaps.adapters.in.api;

import java.time.LocalDateTime;
import java.util.Objects;

public class DynamicPriceRequest {

    private Long id;

    private int dayOfWeek;

    private String startHour;

    private String endHour;

    private double minPrice;

    private double maxPrice;

    private LocalDateTime createAt;


    /**
     * Empty constructor
     */
    public DynamicPriceRequest() {
    }

    /**
     * Constructor with only the id
     * @param id of Dynamic Price
     */
    public DynamicPriceRequest(Long id){
        this.id=id;
    }

    /**
     * Constructor with all the parameters
     * @param id of Dynamic Price
     * @param dayOfWeek of Dynamic Price
     * @param startHour of Dynamic Price
     * @param endHour of Dynamic Price
     * @param minPrice of Dynamic Price
     * @param maxPrice of Dynamic Price
     * @param createAt of Dynamic Price
     */
    public DynamicPriceRequest(Long id, int dayOfWeek, String startHour, String endHour, double minPrice, double maxPrice, LocalDateTime createAt) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.startHour = startHour;
        this.endHour = endHour;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.createAt = createAt;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DynamicPriceRequest)) {
            return false;
        }
        DynamicPriceRequest dynamicPriceRequest = (DynamicPriceRequest) o;
        return Objects.equals(id, dynamicPriceRequest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
}

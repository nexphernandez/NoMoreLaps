package com.nomorelaps.adapters.out.persistence.jpa;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

/**
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Entity(name = "dynamic_price")
public class DynamicPriceJpaEntity {
    
    @Id
    private Long id;

    @Column(name = "day_of_week")
    private int dayOfWeek;

    @Column(name="start_hour")
    private String startHour;

    @Column(name="end_hour")
    private String endHour;

    @Column (name = "min_price")
    private double minPrice;

    @Column (name = "max_price")
    private double maxPrice;

    @Column(name = "created_at")
    private LocalDate createAt;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private ParkingJpaEntity parking;

    /**
     * Empty constructor 
     */
    public DynamicPriceJpaEntity() {
    }
    

    /**
     * Constructor with the dynamic price primary key
     * @param id dynamic price id
     */
    public DynamicPriceJpaEntity(Long id) {
        this.id = id;
    }

    /**
     * Constructor with the dynamic atributes
     * @param id dynamic price id
     * @param dayOfWeek dynamic price dayOfWeek
     * @param startHour dynamic price startHour
     * @param endHour dynamic price endHour
     * @param minPrice dynamic price minPrice
     * @param maxPrice dynamic price maxPrice
     * @param createAt dynamic price createAt
     * @param parking dynamic price parking
     */
    public DynamicPriceJpaEntity(Long id, int dayOfWeek, String startHour, String endHour, double minPrice, double maxPrice, 
        LocalDate createAt, ParkingJpaEntity parking) {
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

    public LocalDate getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public ParkingJpaEntity getParking() {
        return this.parking;
    }

    public void setParking(ParkingJpaEntity parking) {
        this.parking = parking;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DynamicPriceJpaEntity)) {
            return false;
        }
        DynamicPriceJpaEntity dynamicPriceJpaEntity = (DynamicPriceJpaEntity) o;
        return Objects.equals(id, dynamicPriceJpaEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

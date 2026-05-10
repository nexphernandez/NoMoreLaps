package com.nomorelaps.adapters.out.persistence.jpa;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.util.Objects;

/**
 * JPA Entity representing a Parking in the database.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Entity
@Table(name = "parking")
public class ParkingJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "opening_time")
    private LocalDateTime openingTime;

    @Column(name = "closing_time")
    private LocalDateTime closingTime;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "price_per_hour")
    private Double pricePerHour = 2.0;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyJpaEntity company;

    @Column(name = "sanction_amount")
    private Double sanctionAmount = 0.0;

    @Column(name = "sanction_interval")
    private Integer sanctionIntervalInMinutes = 15;

    @Column(name = "total_spots")
    private Integer totalSpots;

    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL)
    private Set<ParkingSpotJpaEntity> parkingSpots;

    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL)
    private Set<DynamicPriceJpaEntity> dynamicPrice;

    /**
     * Empty constructor 
     */
    public ParkingJpaEntity() {
    }

    /**
     * Constructor with the parking primary key
     * @param id parking id
     */
    public ParkingJpaEntity(Long id) {
        this.id  = id;
    }

    /**
     * Constructor with the parking attributes
     * @param id parking id
     * @param address parking address
     * @param name parking name
     * @param latitude latitude in degrees of the parking
     * @param longitude longitude in degrees of the parking
     * @param openingTime parking opening time
     * @param closingTime parking closing time
     * @param createdAt    creation date of parking
     * @param pricePerHour price per hour
     * @param company      parking company
     * @param sanctionAmount amount for each sanction interval
     * @param sanctionIntervalInMinutes interval in minutes for sanctions
     * @param parkingSpots available physical spots inside
     * @param dynamicPrice pricing algorithm configurations
     */
    public ParkingJpaEntity(Long id, String address, String name, Double latitude, Double longitude, LocalDateTime openingTime, 
        LocalDateTime closingTime, LocalDateTime createdAt, Double pricePerHour, CompanyJpaEntity company, 
        Double sanctionAmount, Integer sanctionIntervalInMinutes,
        Set<ParkingSpotJpaEntity> parkingSpots, Set<DynamicPriceJpaEntity> dynamicPrice) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.createdAt = createdAt;
        this.pricePerHour = pricePerHour;
        this.company = company;
        this.sanctionAmount = sanctionAmount;
        this.sanctionIntervalInMinutes = sanctionIntervalInMinutes;
        this.parkingSpots = parkingSpots;
        this.dynamicPrice = dynamicPrice;
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

    public CompanyJpaEntity getCompany() {
        return this.company;
    }

    public void setCompany(CompanyJpaEntity company) {
        this.company = company;
    }

    public Set<ParkingSpotJpaEntity> getParkingSpots() {
        return this.parkingSpots;
    }

    public void setParkingSpots(Set<ParkingSpotJpaEntity> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public Set<DynamicPriceJpaEntity> getDynamicPrice() {
        return this.dynamicPrice;
    }

    public void setDynamicPrice(Set<DynamicPriceJpaEntity> dynamicPrice) {
        this.dynamicPrice = dynamicPrice;
    }

    public Double getPricePerHour() {
        return this.pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
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

    public Integer getTotalSpots() {
        return totalSpots;
    }

    public void setTotalSpots(Integer totalSpots) {
        this.totalSpots = totalSpots;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ParkingJpaEntity)) {
            return false;
        }
        ParkingJpaEntity parkingJpaEntity = (ParkingJpaEntity) o;
        return Objects.equals(id, parkingJpaEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Life-cycle callback method called before the entity is persisted.
     * Automatically sets the creation date if it hasn't been set yet.
     */
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

}

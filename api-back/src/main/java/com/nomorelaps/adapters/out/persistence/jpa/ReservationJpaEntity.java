package com.nomorelaps.adapters.out.persistence.jpa;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * JPA Entity representing a Reservation in the database.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Entity
@Table(name = "reservation")
public class ReservationJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "total_price")
    private double price;

    @Column(name = "status")
    private String state;

    @Column(name = "created_at")
    private LocalDateTime creationTime;

    @ManyToOne
    @JoinColumn(name = "parking_spot_id")
    private ParkingSpotJpaEntity parkingSpot;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    @OneToMany(mappedBy = "reservation")
    private Set<SanctionJpaEntity> sanctions;

    /**
     * Empty constructor
     */
    public ReservationJpaEntity() {
    }

    /**
     * Constructor with the reservation primary key
     * 
     * @param id reservation id
     */
    public ReservationJpaEntity(Long id) {
        this.id = id;
    }

    /**
     * Constructor with the reservation attributes.
     * 
     * @param id           reservation id
     * @param startTime    reservation arrival time
     * @param endTime      reservation departure time
     * @param price        reservation price
     * @param state        reservation state
     * @param creationTime reservation creation time
     * @param parkingSpot  reservation parking spot
     * @param user         reservation user
     * @param sanctions    reservation sanctions
     */
    public ReservationJpaEntity(Long id, LocalDateTime startTime, LocalDateTime endTime, double price, String state,
            LocalDateTime creationTime, ParkingSpotJpaEntity parkingSpot, UserJpaEntity user,
            Set<SanctionJpaEntity> sanctions) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.state = state;
        this.creationTime = creationTime;
        this.parkingSpot = parkingSpot;
        this.user = user;
        this.sanctions = sanctions;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public ParkingSpotJpaEntity getParkingSpot() {
        return this.parkingSpot;
    }

    public void setParkingSpot(ParkingSpotJpaEntity parkingSpot) {
        this.parkingSpot = parkingSpot;
    }

    public UserJpaEntity getUser() {
        return this.user;
    }

    public void setUser(UserJpaEntity user) {
        this.user = user;
    }

    public Set<SanctionJpaEntity> getSanctions() {
        return this.sanctions;
    }

    public void setSanctions(Set<SanctionJpaEntity> sanctions) {
        this.sanctions = sanctions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ReservationJpaEntity)) {
            return false;
        }
        ReservationJpaEntity reservationJpaEntity = (ReservationJpaEntity) o;
        return Objects.equals(id, reservationJpaEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Life-cycle callback method called before the entity is persisted.
     * Automatically sets the creation time if it hasn't been set yet.
     */
    @jakarta.persistence.PrePersist
    protected void onCreate() {
        if (this.creationTime == null) {
            this.creationTime = java.time.LocalDateTime.now();
        }
    }

}

package com.nomorelaps.adapters.out.persistence.jpa;

import java.time.LocalDateTime;
import java.util.Objects;
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

/**
 * JPA Entity representing a ParkingSpot in the database.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Entity
@Table(name = "parking_spot")
public class ParkingSpotJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "state")
    private boolean state = true;

    @Column(name = "spot_number")
    private int number;

    @Column(name = "created_at")
    private LocalDateTime registerDate;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private ParkingJpaEntity parking;

    @OneToMany(mappedBy = "parkingSpot", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReservationJpaEntity> reservations;

    /**
     * Empty constructor 
     */
    public ParkingSpotJpaEntity() {
    }

    /**
     * Constructor with the parking spot primary key
     * @param id parking spot id
     */
    public ParkingSpotJpaEntity(Long id) {
        this.id = id;
    }

    /**
     * Constructor with the parking spot atributes
     * @param id parking spot id
     * @param state parking spot state
     * @param number parking spot number
     * @param registerDate parking spot register date
     * @param parking parking of parkingSpot
     * @param reservations parkingSpot reservations
     */
    public ParkingSpotJpaEntity(Long id, boolean state, int number, LocalDateTime registerDate, 
        ParkingJpaEntity parking, Set<ReservationJpaEntity> reservations) {
        this.id = id;
        this.state = state;
        this.number = number;
        this.registerDate = registerDate;
        this.parking = parking;
        this.reservations = reservations;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isState() {
        return this.state;
    }

    public boolean getState() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public LocalDateTime getRegisterDate() {
        return this.registerDate;
    }

    public void setRegisterDate(LocalDateTime registerDate) {
        this.registerDate = registerDate;
    }

    public ParkingJpaEntity getParking() {
        return this.parking;
    }

    public void setParking(ParkingJpaEntity parking) {
        this.parking = parking;
    }

    public Set<ReservationJpaEntity> getReservations() {
        return this.reservations;
    }

    public void setReservations(Set<ReservationJpaEntity> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ParkingSpotJpaEntity)) {
            return false;
        }
        ParkingSpotJpaEntity parkingSpotJpaEntity = (ParkingSpotJpaEntity) o;
        return Objects.equals(id, parkingSpotJpaEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Life-cycle callback method called before the entity is persisted.
     * Automatically sets the registration date if it hasn't been set yet.
     */
    @PrePersist
    protected void onCreate() {
        if (this.registerDate == null) {
            this.registerDate = LocalDateTime.now();
        }
    }

}

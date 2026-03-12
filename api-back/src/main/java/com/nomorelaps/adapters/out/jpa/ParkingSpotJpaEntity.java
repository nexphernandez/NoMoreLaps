package com.nomorelaps.adapters.out.jpa;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Entity(name = "parking_spot")
public class ParkingSpotJpaEntity {

    @Id
    private Long id;

    @Column(name = "state")
    private boolean state;

    @Column(name = "spot_number")
    private int number;

    @Column(name = "created_at")
    private LocalDate registerDate;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    private ParkingJpaEntity parking;

    @OneToMany(mappedBy = "reservation")
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
    public ParkingSpotJpaEntity(Long id, boolean state, int number, LocalDate registerDate, 
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

    public LocalDate getRegisterDate() {
        return this.registerDate;
    }

    public void setRegisterDate(LocalDate registerDate) {
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

}

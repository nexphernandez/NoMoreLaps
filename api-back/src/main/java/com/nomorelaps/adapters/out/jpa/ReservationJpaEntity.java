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
@Entity(name = "reservation")
public class ReservationJpaEntity {
    @Id
    private Long id;

    @Column(name = "start_time")
    private LocalDate arrivalTime;

    @Column(name = "end_time")
    private LocalDate departureTime;

    @Column(name = "total_price")
    private double price;

    @Column(name = "status")
    private String State;

    @Column(name = "start_time")
    private LocalDate creationTime;

    @ManyToOne
    @JoinColumn(name = "parking_spot_id")
    private ParkingSpotJpaEntity parkingSpot;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    @OneToMany(mappedBy = "sanction")
    private Set<SanctionJpaEntity>sanctions;

    /**
     * Empty constructor 
     */
    public ReservationJpaEntity() {
    }

    /**
     * Constructor with the reservation primary key
     * @param id reservation id
     */
    public ReservationJpaEntity(Long id) {
        this.id = id;
    }

    /**
     * Constructor with the reservation atributes
     * @param id reservation spot id
     * @param arrivalTime reservation arrival time 
     * @param departureTime reservation departure time 
     * @param price reservation price 
     * @param State reservation State
     * @param creationTime reservation creationTime
     * @param parkingSpot reservation parking spot 
     * @param user reservation user
     * @param sanctions reservation sactions
     */
    public ReservationJpaEntity(Long id, LocalDate arrivalTime, LocalDate departureTime, double price, String State, 
        LocalDate creationTime, ParkingSpotJpaEntity parkingSpot, UserJpaEntity user, Set<SanctionJpaEntity> sanctions) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.price = price;
        this.State = State;
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

    public LocalDate getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(LocalDate arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDate getDepartureTime() {
        return this.departureTime;
    }

    public void setDepartureTime(LocalDate departureTime) {
        this.departureTime = departureTime;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getState() {
        return this.State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public LocalDate getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(LocalDate creationTime) {
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
        return Objects.equals(id, reservationJpaEntity.id) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

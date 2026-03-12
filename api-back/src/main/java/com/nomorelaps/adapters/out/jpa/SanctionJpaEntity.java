package com.nomorelaps.adapters.out.jpa;

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
@Entity(name = "sanction")
public class SanctionJpaEntity {
    @Id
    private Long id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "reason")
    private String reason;

    @Column(name = "paid")
    private boolean paid;

    @Column(name = "created_at")
    private LocalDate arrivalTime;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private ReservationJpaEntity reservation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserJpaEntity user;

    /**
     * Empty constructor 
     */
    public SanctionJpaEntity() {
    }

    /**
     * Constructor with the sanction primary key
     * @param id sanction id
     */
    public SanctionJpaEntity(Long id) {
        this.id = id;
    }

    /**
     * Constructor with the sanction atributes
     * @param id sanction id
     * @param amount sanction amount
     * @param reason sanction reason
     * @param paid sanction paid
     * @param arrivalTime sanction arrivalTime
     * @param reservation sanction reservation
     * @param user sanction user
     */
    public SanctionJpaEntity(Long id, double amount, String reason, boolean paid, LocalDate arrivalTime, 
        ReservationJpaEntity reservation, UserJpaEntity user) {
        this.id = id;
        this.amount = amount;
        this.reason = reason;
        this.paid = paid;
        this.arrivalTime = arrivalTime;
        this.reservation = reservation;
        this.user = user;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isPaid() {
        return this.paid;
    }

    public boolean getPaid() {
        return this.paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public LocalDate getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(LocalDate arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public ReservationJpaEntity getReservation() {
        return this.reservation;
    }

    public void setReservation(ReservationJpaEntity reservation) {
        this.reservation = reservation;
    }

    public UserJpaEntity getUser() {
        return this.user;
    }

    public void setUser(UserJpaEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SanctionJpaEntity)) {
            return false;
        }
        SanctionJpaEntity sanctionJpaEntity = (SanctionJpaEntity) o;
        return Objects.equals(id, sanctionJpaEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}

package com.nomorelaps.domain.models;

import java.time.LocalDateTime;

/**
 * Domain model representing a Sanction applied to a User.
 * Contains the pure business logic and attributes, independent of databases or
 * APIs.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class Sanction {
    private Long id;
    private double amount;
    private String reason;
    private boolean paid;
    private LocalDateTime arrivalTime;
    private Reservation reservation;
    private User user;

    /**
     * Empty constructor
     */
    public Sanction() {
    }

    /**
     * Constructor with the sanction primary key
     * @param id sanction id
     */
    public Sanction(Long id) {
        this.id = id;
    }

    /**
     * Constructor with all attributes
     * 
     * @param id          sanction unique id
     * @param amount      economic penalty
     * @param reason      text explaining sanction motif
     * @param paid        payment resolution state
     * @param arrivalTime audit trigger moment
     * @param reservation associated reservation context
     * @param user        sanctioned user
     */
    public Sanction(Long id, double amount, String reason, boolean paid, LocalDateTime arrivalTime,
            Reservation reservation, User user) {
        this.id = id;
        this.amount = amount;
        this.reason = reason;
        this.paid = paid;
        this.arrivalTime = arrivalTime;
        this.reservation = reservation;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

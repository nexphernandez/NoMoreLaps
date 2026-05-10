package com.nomorelaps.adapters.in.api;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) representing a Sanction in API responses.
 * Encapsulates the structured data sent back to the client.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class SanctionResponse {

    private Long id;
    private double amount;
    private String reason;
    private boolean paid;
    private LocalDateTime arrivalTime;
    private Long userId;
    private String userName;
    private String parkingName;

    /**
     * Empty constructor
     */
    public SanctionResponse() {
    }

    /**
     * Constructor with the sanction primary key
     * @param id sanction id
     */
    public SanctionResponse(Long id) {
        this.id = id;
    }

    /**
     * Constructor with all the attributes
     * @param id of the sanction
     * @param amount of the sanction
     * @param reason of the sanction
     * @param paid status of the sanction
     * @param arrivalTime of the sanction
     */
    public SanctionResponse(Long id, double amount, String reason, boolean paid, LocalDateTime arrivalTime, Long userId, String userName, String parkingName) {
        this.id = id;
        this.amount = amount;
        this.reason = reason;
        this.paid = paid;
        this.arrivalTime = arrivalTime;
        this.userId = userId;
        this.userName = userName;
        this.parkingName = parkingName;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SanctionResponse)) return false;
        SanctionResponse that = (SanctionResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

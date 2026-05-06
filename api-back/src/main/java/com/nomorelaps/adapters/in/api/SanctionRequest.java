package com.nomorelaps.adapters.in.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Data Transfer Object (DTO) for creating or updating a Sanction.
 * Encapsulates the necessary request data sent by the client.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class SanctionRequest {

    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotBlank(message = "Reason cannot be empty")
    private String reason;

    private boolean paid;

    @NotNull(message = "Reservation ID is mandatory")
    private Long reservationId;

    @NotNull(message = "User ID is mandatory")
    private Long userId;

    /**
     * Empty constructor
     */
    public SanctionRequest() {
    }

    /**
     * Constructor with all the attributes
     * @param amount of the sanction
     * @param reason of the sanction
     * @param paid status of the sanction
     * @param reservationId of the sanction
     * @param userId of the user sanctioned
     */
    public SanctionRequest(double amount, String reason, boolean paid, Long reservationId, Long userId) {
        this.amount = amount;
        this.reason = reason;
        this.paid = paid;
        this.reservationId = reservationId;
        this.userId = userId;
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

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

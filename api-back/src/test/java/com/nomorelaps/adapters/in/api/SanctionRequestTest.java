package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for SanctionRequest DTO.
 * Uses meaningful names and granular tests to verify property mapping.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class SanctionRequestTest {

    private SanctionRequest sanctionRequest;

    @BeforeEach
    void setUp() {
        sanctionRequest = new SanctionRequest();
    }

    @Test
    @DisplayName("Constructor - Should correctly map all provided fields")
    void shouldInitializeWithParameterizedConstructor() {
        SanctionRequest parameterizedRequest = new SanctionRequest(75.50, "Parking damage", true, 1000L, 5000L);

        assertEquals(75.50, parameterizedRequest.getAmount(), "Amount should match constructor argument");
        assertEquals("Parking damage", parameterizedRequest.getReason(), "Reason should match constructor argument");
        assertTrue(parameterizedRequest.isPaid(), "Paid status should match constructor argument");
        assertEquals(1000L, parameterizedRequest.getReservationId(), "Reservation ID should match constructor argument");
        assertEquals(5000L, parameterizedRequest.getUserId(), "User ID should match constructor argument");
    }

    @Test
    @DisplayName("amount - Should set and get sanction amount")
    void shouldSetAndGetAmount() {
        sanctionRequest.setAmount(100.0);
        assertEquals(100.0, sanctionRequest.getAmount());
    }

    @Test
    @DisplayName("reason - Should set and get sanction reason")
    void shouldSetAndGetReason() {
        sanctionRequest.setReason("Expired time");
        assertEquals("Expired time", sanctionRequest.getReason());
    }

    @Test
    @DisplayName("isPaid - Should set and get payment status")
    void shouldSetAndGetIsPaid() {
        sanctionRequest.setPaid(true);
        assertTrue(sanctionRequest.isPaid());
        
        sanctionRequest.setPaid(false);
        assertFalse(sanctionRequest.isPaid());
    }

    @Test
    @DisplayName("reservationId - Should set and get reservation reference")
    void shouldSetAndGetReservationId() {
        sanctionRequest.setReservationId(99L);
        assertEquals(99L, sanctionRequest.getReservationId());
    }

    @Test
    @DisplayName("userId - Should set and get user reference")
    void shouldSetAndGetUserId() {
        sanctionRequest.setUserId(88L);
        assertEquals(88L, sanctionRequest.getUserId());
    }
}

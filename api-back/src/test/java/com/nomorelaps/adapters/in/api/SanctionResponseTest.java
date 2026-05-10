package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for SanctionResponse DTO.
 * Verifies constructors, property accessors, and logical equality.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class SanctionResponseTest {

    private SanctionResponse sanctionResponse;
    private LocalDateTime arrivalTime;

    @BeforeEach
    void setUp() {
        sanctionResponse = new SanctionResponse();
        arrivalTime = LocalDateTime.now();
    }

    @Test
    @DisplayName("Constructor(id) - Should initialize with ID and default paid status")
    void shouldInitializeWithIdConstructor() {
        SanctionResponse idOnlyResponse = new SanctionResponse(500L);
        assertEquals(500L, idOnlyResponse.getId());
        assertFalse(idOnlyResponse.isPaid());
    }

    @Test
    @DisplayName("Full Constructor - Should map all provided fields correctly")
    void shouldInitializeWithFullConstructor() {
        SanctionResponse fullResponse = new SanctionResponse(1L, 50.0, "Speeding", true, arrivalTime, 10L, "John", "Parking A");

        assertEquals(1L, fullResponse.getId());
        assertEquals(50.0, fullResponse.getAmount());
        assertEquals("Speeding", fullResponse.getReason());
        assertTrue(fullResponse.isPaid());
        assertEquals(arrivalTime, fullResponse.getArrivalTime());
        assertEquals(10L, fullResponse.getUserId());
        assertEquals("John", fullResponse.getUserName());
        assertEquals("Parking A", fullResponse.getParkingName());
    }

    @Test
    @DisplayName("id - Should set and get sanction ID")
    void shouldSetAndGetId() {
        sanctionResponse.setId(10L);
        assertEquals(10L, sanctionResponse.getId());
    }

    @Test
    @DisplayName("amount - Should set and get amount")
    void shouldSetAndGetAmount() {
        sanctionResponse.setAmount(15.0);
        assertEquals(15.0, sanctionResponse.getAmount());
    }

    @Test
    @DisplayName("reason - Should set and get reason")
    void shouldSetAndGetReason() {
        sanctionResponse.setReason("Late exit");
        assertEquals("Late exit", sanctionResponse.getReason());
    }

    @Test
    @DisplayName("isPaid - Should set and get payment status")
    void shouldSetAndGetIsPaid() {
        sanctionResponse.setPaid(true);
        assertTrue(sanctionResponse.isPaid());
    }

    @Test
    @DisplayName("equals - Should be equal for identical objects")
    void shouldBeEqualForIdenticalObjects() {
        SanctionResponse firstResponse = new SanctionResponse(1L, 20.0, "Reason", true, arrivalTime, 5L, "User", "Park");
        SanctionResponse secondResponse = new SanctionResponse(1L, 20.0, "Reason", true, arrivalTime, 5L, "User", "Park");

        assertEquals(firstResponse, firstResponse, "Should be equal to itself");
        assertEquals(firstResponse, secondResponse, "Should be equal if all fields match");
    }

    @Test
    @DisplayName("equals - Should not be equal if IDs differ")
    void shouldNotBeEqualIfIdsDiffer() {
        SanctionResponse baseResponse = new SanctionResponse(1L, 20.0, "Reason", true, arrivalTime, 5L, "User", "Park");
        SanctionResponse differentId = new SanctionResponse(2L, 20.0, "Reason", true, arrivalTime, 5L, "User", "Park");
        
        assertNotEquals(baseResponse, differentId);
    }

    @Test
    @DisplayName("equals - Should be equal if IDs match regardless of other fields")
    void shouldBeEqualIfIdsMatch() {
        SanctionResponse baseResponse = new SanctionResponse(1L, 20.0, "Reason", true, arrivalTime, 5L, "User", "Park");
        SanctionResponse sameIdDifferentAmount = new SanctionResponse(1L, 30.0, "Other", false, arrivalTime.plusDays(1), 10L, "Other", "Other");

        assertEquals(baseResponse, sameIdDifferentAmount, "Should be equal because implementation only compares ID");
    }

    @Test
    @DisplayName("equals - Should not be equal to different object types")
    void shouldNotBeEqualToDifferentTypes() {
        SanctionResponse baseResponse = new SanctionResponse(1L);
        
        assertNotEquals(baseResponse, "Not a SanctionResponse", "Should return false when comparing with a different class");
        assertNotEquals(baseResponse, null, "Should return false when comparing with null");
    }

    @Test
    @DisplayName("hashCode - Should be consistent for equal objects")
    void shouldHaveConsistentHashCode() {
        SanctionResponse firstResponse = new SanctionResponse(1L, 20.0, "Reason", true, arrivalTime, 5L, "User", "Park");
        SanctionResponse secondResponse = new SanctionResponse(1L, 20.0, "Reason", true, arrivalTime, 5L, "User", "Park");

        assertEquals(firstResponse.hashCode(), secondResponse.hashCode());
    }
}

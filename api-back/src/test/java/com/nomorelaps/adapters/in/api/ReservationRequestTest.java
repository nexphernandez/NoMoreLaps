package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for ReservationRequest DTO.
 * Verifies that all fields are correctly mapped via constructor and accessors.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class ReservationRequestTest {

    private ReservationRequest request;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        request = new ReservationRequest();
        now = LocalDateTime.now();
    }

    @Test
    @DisplayName("Constructor - Should correctly initialize all fields")
    void shouldInitializeWithFullConstructor() {
        ReservationRequest fullRequest = new ReservationRequest(now, now.plusHours(2), 25.0, "ACTIVE", 1L, 10L);

        assertEquals(now, fullRequest.getStartTime());
        assertEquals(now.plusHours(2), fullRequest.getEndTime());
        assertEquals(25.0, fullRequest.getPrice());
        assertEquals("ACTIVE", fullRequest.getState());
        assertEquals(1L, fullRequest.getUserId());
        assertEquals(10L, fullRequest.getParkingSpotId());
    }

    @Test
    @DisplayName("startTime - Should set and get start time")
    void shouldSetAndGetStartTime() {
        request.setStartTime(now);
        assertEquals(now, request.getStartTime());
    }

    @Test
    @DisplayName("endTime - Should set and get end time")
    void shouldSetAndGetEndTime() {
        request.setEndTime(now.plusDays(1));
        assertEquals(now.plusDays(1), request.getEndTime());
    }

    @Test
    @DisplayName("price - Should set and get price")
    void shouldSetAndGetPrice() {
        request.setPrice(15.99);
        assertEquals(15.99, request.getPrice());
    }

    @Test
    @DisplayName("state - Should set and get state")
    void shouldSetAndGetState() {
        request.setState("PENDING");
        assertEquals("PENDING", request.getState());
    }

    @Test
    @DisplayName("parkingSpotId - Should set and get parking spot ID")
    void shouldSetAndGetParkingSpotId() {
        request.setParkingSpotId(101L);
        assertEquals(101L, request.getParkingSpotId());
    }

    @Test
    @DisplayName("userId - Should set and get user ID")
    void shouldSetAndGetUserId() {
        request.setUserId(500L);
        assertEquals(500L, request.getUserId());
    }
}

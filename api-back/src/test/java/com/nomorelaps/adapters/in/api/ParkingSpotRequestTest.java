package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for ParkingSpotRequest DTO.
 * Verifies that parking spot properties are correctly handled.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class ParkingSpotRequestTest {

    private ParkingSpotRequest spotRequest;

    @BeforeEach
    void setUp() {
        spotRequest = new ParkingSpotRequest();
    }

    @Test
    @DisplayName("Constructor - Should correctly initialize state and number")
    void shouldInitializeWithParameterizedConstructor() {
        ParkingSpotRequest fullRequest = new ParkingSpotRequest(true, 101);

        assertTrue(fullRequest.getState());
        assertEquals(101, fullRequest.getNumber());
    }

    @Test
    @DisplayName("state - Should set and get the spot status")
    void shouldSetAndGetState() {
        spotRequest.setState(true);
        assertTrue(spotRequest.getState());
        assertTrue(spotRequest.isState(), "isState should return same as getState");
        
        spotRequest.setState(false);
        assertFalse(spotRequest.getState());
    }

    @Test
    @DisplayName("number - Should set and get the spot number")
    void shouldSetAndGetNumber() {
        spotRequest.setNumber(55);
        assertEquals(55, spotRequest.getNumber());
    }
}

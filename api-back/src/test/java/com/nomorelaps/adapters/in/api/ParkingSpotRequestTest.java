package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkingSpotRequestTest {

    @Test
    @DisplayName("Should test getters, setters and empty constructor for ParkingSpotRequest")
    void testGettersAndSetters() {
        ParkingSpotRequest dto = new ParkingSpotRequest();
        assertNotNull(dto);

        dto.setState(true);
        assertTrue(dto.isState());
        assertTrue(dto.getState()); // covers getState()
        dto.setState(false);
        assertFalse(dto.getState());
        dto.setNumber(1);
        assertEquals(1, dto.getNumber());
    }

    @Test
    @DisplayName("Should test parameterized constructor")
    void testParameterizedConstructor() {
        ParkingSpotRequest dto = new ParkingSpotRequest(true, 5);
        assertTrue(dto.isState());
        assertEquals(5, dto.getNumber());
    }

}


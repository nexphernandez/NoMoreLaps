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

        dto.setState(false);
        assertEquals(false, dto.isState());
        dto.setNumber(1);
        assertEquals(1, dto.getNumber());
    }

}

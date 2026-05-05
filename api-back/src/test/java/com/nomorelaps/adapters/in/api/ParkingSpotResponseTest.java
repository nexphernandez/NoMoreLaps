package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class ParkingSpotResponseTest {

    @Test
    @DisplayName("Should test getters, setters and empty constructor for ParkingSpotResponse")
    void testGettersAndSetters() {
        ParkingSpotResponse dto = new ParkingSpotResponse();
        assertNotNull(dto);

        dto.setId(1L);
        assertEquals(1L, dto.getId());
        dto.setState(false);
        assertEquals(false, dto.isState());
        dto.setNumber(1);
        assertEquals(1, dto.getNumber());
        dto.setRegisterDate(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        assertEquals(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1), dto.getRegisterDate());
        
        dto.setState(true);
        assertTrue(dto.getState()); // Tests getState() and isState() since we set it to true
    }

    @Test
    @DisplayName("Should test parameterized constructors")
    void testConstructors() {
        ParkingSpotResponse dto1 = new ParkingSpotResponse(1L);
        assertEquals(1L, dto1.getId());
        assertFalse(dto1.getState());

        LocalDateTime now = LocalDateTime.now();
        ParkingSpotResponse dto2 = new ParkingSpotResponse(2L, true, 10, now);
        assertEquals(2L, dto2.getId());
        assertTrue(dto2.getState());
        assertEquals(10, dto2.getNumber());
        assertEquals(now, dto2.getRegisterDate());
    }


    @Test
    @DisplayName("Should test equals and hashCode branches for ParkingSpotResponse")
    void testEqualsAndHashCode() {
        ParkingSpotResponse dto1 = new ParkingSpotResponse();
        ParkingSpotResponse dto2 = new ParkingSpotResponse();
        ParkingSpotResponse dto3 = new ParkingSpotResponse();

        dto1.setId(1L);
        dto2.setId(1L);
        dto3.setId(2L);
        dto1.setState(false);
        dto2.setState(false);
        dto3.setState(true);
        dto1.setNumber(1);
        dto2.setNumber(1);
        dto3.setNumber(2);
        dto1.setRegisterDate(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        dto2.setRegisterDate(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        dto3.setRegisterDate(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(2));

        // Base checks
        assertEquals(dto1, dto1);
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, new Object());
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // Branch coverage for each field
        ParkingSpotResponse tempid = new ParkingSpotResponse();
        tempid.setId(1L);
        tempid.setState(false);
        tempid.setNumber(1);
        tempid.setRegisterDate(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        tempid.setId(null);
        dto1.equals(tempid);
        tempid.equals(dto1);
        tempid.setId(2L);
        dto1.equals(tempid);

        ParkingSpotResponse tempstate = new ParkingSpotResponse();
        tempstate.setId(1L);
        tempstate.setState(false);
        tempstate.setNumber(1);
        tempstate.setRegisterDate(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        tempstate.setState(true);
        dto1.equals(tempstate);

        ParkingSpotResponse tempnumber = new ParkingSpotResponse();
        tempnumber.setId(1L);
        tempnumber.setState(false);
        tempnumber.setNumber(1);
        tempnumber.setRegisterDate(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        tempnumber.setNumber(2);
        dto1.equals(tempnumber);

        ParkingSpotResponse tempregisterDate = new ParkingSpotResponse();
        tempregisterDate.setId(1L);
        tempregisterDate.setState(false);
        tempregisterDate.setNumber(1);
        tempregisterDate.setRegisterDate(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        tempregisterDate.setRegisterDate(null);
        dto1.equals(tempregisterDate);
        tempregisterDate.equals(dto1);
        tempregisterDate.setRegisterDate(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(2));
        dto1.equals(tempregisterDate);

    }
}

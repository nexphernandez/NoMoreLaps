package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class SanctionResponseTest {

    @Test
    @DisplayName("Should test getters, setters and empty constructor for SanctionResponse")
    void testGettersAndSetters() {
        SanctionResponse dto = new SanctionResponse();
        assertNotNull(dto);

        dto.setId(1L);
        assertEquals(1L, dto.getId());
        dto.setAmount(1.0);
        assertEquals(1.0, dto.getAmount());
        dto.setReason("dummy1");
        assertEquals("dummy1", dto.getReason());
        dto.setPaid(false);
        assertEquals(false, dto.isPaid());
        dto.setArrivalTime(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        assertEquals(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1), dto.getArrivalTime());
    }

    @Test
    @DisplayName("Should test parameterized constructors")
    void testConstructors() {
        SanctionResponse dto1 = new SanctionResponse(1L);
        assertEquals(1L, dto1.getId());
        assertFalse(dto1.isPaid());

        LocalDateTime now = LocalDateTime.now();
        SanctionResponse dto2 = new SanctionResponse(2L, 50.0, "Late", true, now);
        assertEquals(2L, dto2.getId());
        assertEquals(50.0, dto2.getAmount());
        assertEquals("Late", dto2.getReason());
        assertTrue(dto2.isPaid());
        assertEquals(now, dto2.getArrivalTime());
    }


    @Test
    @DisplayName("Should test equals and hashCode branches for SanctionResponse")
    void testEqualsAndHashCode() {
        SanctionResponse dto1 = new SanctionResponse();
        SanctionResponse dto2 = new SanctionResponse();
        SanctionResponse dto3 = new SanctionResponse();

        dto1.setId(1L);
        dto2.setId(1L);
        dto3.setId(2L);
        dto1.setAmount(1.0);
        dto2.setAmount(1.0);
        dto3.setAmount(2.0);
        dto1.setReason("dummy1");
        dto2.setReason("dummy1");
        dto3.setReason("dummy2");
        dto1.setPaid(false);
        dto2.setPaid(false);
        dto3.setPaid(true);
        dto1.setArrivalTime(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        dto2.setArrivalTime(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        dto3.setArrivalTime(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(2));

        // Base checks
        assertEquals(dto1, dto1);
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, new Object());
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // Branch coverage for each field
        SanctionResponse tempid = new SanctionResponse();
        tempid.setId(1L);
        tempid.setAmount(1.0);
        tempid.setReason("dummy1");
        tempid.setPaid(false);
        tempid.setArrivalTime(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        tempid.setId(null);
        dto1.equals(tempid);
        tempid.equals(dto1);
        tempid.setId(2L);
        dto1.equals(tempid);

        SanctionResponse tempamount = new SanctionResponse();
        tempamount.setId(1L);
        tempamount.setAmount(1.0);
        tempamount.setReason("dummy1");
        tempamount.setPaid(false);
        tempamount.setArrivalTime(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        tempamount.setAmount(2.0);
        dto1.equals(tempamount);

        SanctionResponse tempreason = new SanctionResponse();
        tempreason.setId(1L);
        tempreason.setAmount(1.0);
        tempreason.setReason("dummy1");
        tempreason.setPaid(false);
        tempreason.setArrivalTime(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        tempreason.setReason(null);
        dto1.equals(tempreason);
        tempreason.equals(dto1);
        tempreason.setReason("dummy2");
        dto1.equals(tempreason);

        SanctionResponse temppaid = new SanctionResponse();
        temppaid.setId(1L);
        temppaid.setAmount(1.0);
        temppaid.setReason("dummy1");
        temppaid.setPaid(false);
        temppaid.setArrivalTime(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        temppaid.setPaid(true);
        dto1.equals(temppaid);

        SanctionResponse temparrivalTime = new SanctionResponse();
        temparrivalTime.setId(1L);
        temparrivalTime.setAmount(1.0);
        temparrivalTime.setReason("dummy1");
        temparrivalTime.setPaid(false);
        temparrivalTime.setArrivalTime(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        temparrivalTime.setArrivalTime(null);
        dto1.equals(temparrivalTime);
        temparrivalTime.equals(dto1);
        temparrivalTime.setArrivalTime(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(2));
        dto1.equals(temparrivalTime);

    }
}

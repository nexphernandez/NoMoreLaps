package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SanctionRequestTest {

    @Test
    @DisplayName("Should test getters, setters and empty constructor for SanctionRequest")
    void testGettersAndSetters() {
        SanctionRequest dto = new SanctionRequest();
        assertNotNull(dto);

        dto.setAmount(1.0);
        assertEquals(1.0, dto.getAmount());
        dto.setReason("dummy1");
        assertEquals("dummy1", dto.getReason());
        dto.setPaid(false);
        assertEquals(false, dto.isPaid());
        dto.setReservationId(1L);
        assertEquals(1L, dto.getReservationId());
        dto.setUserId(1L);
        assertEquals(1L, dto.getUserId());
    }

}

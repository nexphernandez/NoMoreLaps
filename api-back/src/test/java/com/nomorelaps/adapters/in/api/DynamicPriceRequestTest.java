package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DynamicPriceRequestTest {

    @Test
    @DisplayName("Should test getters, setters and empty constructor for DynamicPriceRequest")
    void testGettersAndSetters() {
        DynamicPriceRequest dto = new DynamicPriceRequest();
        assertNotNull(dto);

        dto.setDayOfWeek(1);
        assertEquals(1, dto.getDayOfWeek());
        dto.setStartHour("dummy1");
        assertEquals("dummy1", dto.getStartHour());
        dto.setEndHour("dummy1");
        assertEquals("dummy1", dto.getEndHour());
        dto.setMinPrice(1.0);
        assertEquals(1.0, dto.getMinPrice());
        dto.setMaxPrice(1.0);
        assertEquals(1.0, dto.getMaxPrice());
    }

}
